package com.siriuslab.onec.widget.domain.account.settings.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siriuslab.onec.widget.app.error.ErrorResponse;
import com.siriuslab.onec.widget.domain.account.settings.dto.AccountSettingsUpdateRequest;
import com.siriuslab.onec.widget.domain.account.settings.entity.AccountSettingsEntity;
import com.siriuslab.onec.widget.domain.account.settings.mapper.AccountSettingsMapper;
import com.siriuslab.onec.widget.domain.account.settings.service.AccountSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountSettingsController {

    private final AccountSettingsService accountSettingsService;
    private final ObjectMapper objectMapper;

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping("/{accountId}/settings/")
    public ResponseEntity<?> getAccountSetting(
            @PathVariable("accountId") String accountId) {

        log.info("accountId : {}", accountId);

        try {
            return ResponseEntity.ok(accountSettingsService.getByAccountId(UUID.fromString(accountId)));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResponse.builder()
                            .message("Account with id : " + accountId + " not found for save or update account settings")
                            .build());
        }
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PutMapping("/{accountId}/settings/")
    public ResponseEntity<?> activateApp(
            @PathVariable("accountId") String accountId,
            @RequestBody AccountSettingsUpdateRequest accountSettingsUpdateRequest) throws JsonProcessingException {

        log.info("accountId : {}", accountId);
        log.info("accountSettingsUpdateRequest : {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(accountSettingsUpdateRequest));

        AccountSettingsEntity accountSettingsEntity = AccountSettingsMapper.INSTANCE.mapToEntity(accountSettingsUpdateRequest);

        log.info("accountSettingsEntity : {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(accountSettingsEntity));

        try {
            return ResponseEntity.ok(accountSettingsService.save(UUID.fromString(accountId), accountSettingsEntity));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResponse.builder()
                            .message("Account with id : " + accountId + " not found for save or update account settings")
                            .build());
        }
    }
}
