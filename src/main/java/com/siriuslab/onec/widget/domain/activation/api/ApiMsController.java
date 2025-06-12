package com.siriuslab.onec.widget.domain.activation.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siriuslab.onec.widget.app.aspect.logging.Loggable;
import com.siriuslab.onec.widget.domain.account.settings.entity.AccountSettingsEntity;
import com.siriuslab.onec.widget.domain.account.settings.service.AccountSettingsService;
import com.siriuslab.onec.widget.domain.account.token.entity.AccountEntity;
import com.siriuslab.onec.widget.domain.account.token.service.AccountService;
import com.siriuslab.onec.widget.domain.activation.dto.activate.AppActivationRequest;
import com.siriuslab.onec.widget.domain.activation.dto.activate.AppActivationResponse;
import com.siriuslab.onec.widget.domain.activation.dto.deactivate.DeactivateAppRequest;
import com.siriuslab.onec.widget.domain.activation.dto.status.AppStatusResponse;
import com.siriuslab.onec.widget.domain.activation.entity.ActivationEntity;
import com.siriuslab.onec.widget.domain.activation.mapper.AppActivationMapper;
import com.siriuslab.onec.widget.domain.activation.service.ActivationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/moysklad/vendor/1.0/apps")
@RequiredArgsConstructor
public class ApiMsController {

    private final ActivationService activationService;
    private final AccountService accountService;
    private final AccountSettingsService accountSettingsService;
    private final ObjectMapper objectMapper;

    @Loggable
    @Transactional
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{appId}/{accountId}")
    public ResponseEntity<AppActivationResponse> activateApp(
            @PathVariable("appId") String appId,
            @PathVariable("accountId") String accountId,
            @RequestBody AppActivationRequest activationRequest,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws JsonProcessingException {

        UUID accountUuid = UUID.fromString(accountId);
        activationRequest.setAccountId(accountUuid);

        logRequestDetails(appId, accountId, authorizationHeader, activationRequest);

        ActivationEntity activationEntity = AppActivationMapper.INSTANCE.mapToEntity(activationRequest);
        logEntityDetails("Mapped ActivationEntity", activationEntity);

        ActivationEntity savedActivationEntity = activationService.save(activationEntity);

        Optional<AccountEntity> existingAccount = findAndUpdateExistingAccount(savedActivationEntity, accountUuid, activationRequest);

        if (existingAccount.isEmpty()) {
            createAndSaveNewAccount(savedActivationEntity, activationRequest);
        }

        return ResponseEntity.ok(AppActivationResponse.builder().build());
    }

    @Loggable
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{appId}/{accountId}")
    public ResponseEntity<AppStatusResponse> appStatus(@PathVariable("appId") String appId,
                                                       @PathVariable("accountId") String accountId) {
        return ResponseEntity.ok(AppStatusResponse.builder().build());
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{appUid}/{accountUid}")
    public ResponseEntity<Void> deactivateApp(@PathVariable String appUid,
                                              @PathVariable String accountUid,
                                              @RequestBody DeactivateAppRequest request)
            throws JsonProcessingException {

        log.info("appUid: {}", appUid);
        log.info("accountUid: {}", accountUid);
        log.info("DeactivateAppRequest: {}", objectMapper.writeValueAsString(request));

        accountService.uninstallOrSuspend(appUid, accountUid, request);
        return ResponseEntity.ok().build();
    }

    private void logRequestDetails(String appId, String accountId, String authorizationHeader, AppActivationRequest activationRequest) throws JsonProcessingException {
        log.info("Query params : {} : {}", appId, accountId);
        log.info("Authorization Header : {}", authorizationHeader);
        log.info("Activation Request : {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(activationRequest));
    }

    private void logEntityDetails(String prefix, Object entity) throws JsonProcessingException {
        log.info("{} : {}", prefix, objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(entity));
    }

    private Optional<AccountEntity> findAndUpdateExistingAccount(ActivationEntity savedActivationEntity, UUID accountId, AppActivationRequest activationRequest) {
        return savedActivationEntity.getAccounts().stream()
                .filter(account -> account.getAccountId().equals(accountId))
                .findFirst()
                .map(accountEntity -> {
                    try {
                        updateAccountEntity(accountEntity, savedActivationEntity, activationRequest);
                    } catch (JsonProcessingException e) {
                        log.error("findAndUpdateExistingAccount error :{}", e.getMessage());
                        throw new IllegalArgumentException("Ошибка, при обновлении настроек аккаунта. Обратитесь в службу поддержки.");
                    }
                    saveAccountAndSettings(accountEntity);
                    return accountEntity;
                });
    }

    private void saveAccountAndSettings(AccountEntity accountEntity) {
        try {
            accountService.save(accountEntity);
            accountSettingsService.save(accountEntity.getAccountId(), accountEntity.getSettings());
        } catch (JsonProcessingException e) {
            log.error("Error saving account or settings: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Ошибка, при сохранении настроек аккаунта. Обратитесь в службу поддержки.");
        }
    }

    private void updateAccountEntity(AccountEntity accountEntity,
                                     ActivationEntity savedActivationEntity,
                                     AppActivationRequest activationRequest)
            throws JsonProcessingException {
        accountEntity.setActivated(true);
        accountEntity.setSuspended(false);
        accountEntity.setAccessToken(extractAccessToken(activationRequest));
        accountEntity.setActivation(savedActivationEntity);
        accountService.save(accountEntity);
    }

    private void createAndSaveNewAccount(ActivationEntity savedActivationEntity,
                                         AppActivationRequest activationRequest)
            throws JsonProcessingException {
        AccountEntity newAccountEntity = new AccountEntity();
        newAccountEntity.setAccountId(savedActivationEntity.getAccountId());
        newAccountEntity.setActivated(true);
        newAccountEntity.setSuspended(false);
        newAccountEntity.setAccessToken(extractAccessToken(activationRequest));
        newAccountEntity.setActivation(savedActivationEntity);

        AccountEntity savedAccount = accountService.save(newAccountEntity);
        accountSettingsService.save(savedAccount.getAccountId(), new AccountSettingsEntity());
    }

    private String extractAccessToken(AppActivationRequest activationRequest) {
        return activationRequest.getAccess().stream()
                .map(AppActivationRequest.Access::getAccessToken)
                .filter(token -> token != null && !token.isEmpty())
                .findFirst()
                .orElse(null);
    }
}
