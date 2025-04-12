package com.siriuslab.onec.widget.domain.onec.controller;

import com.siriuslab.onec.widget.app.entity.ResourceNotFoundException;
import com.siriuslab.onec.widget.domain.account.token.entity.AccountEntity;
import com.siriuslab.onec.widget.domain.account.token.service.AccountService;
import com.siriuslab.onec.widget.domain.onec.dto.AccountSubscriptionSettingsRequest;
import com.siriuslab.onec.widget.domain.onec.dto.AccountSubscriptionSettingsResponse;
import com.siriuslab.onec.widget.domain.onec.mapper.AccountSubscriptionSettingsMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/one-c/account/subscription")
@RequiredArgsConstructor
@Validated
public class ApiOneCContoller {

    private final AccountService accountService;

    @Operation(
            summary = "Retrieve account subscription settings",
            description = "Retrieves the subscription settings for an account based on the provided request token.",
            security = @SecurityRequirement(name = "basicAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved subscription settings",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AccountSubscriptionSettingsResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content),
                    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND: Entity could not be found", content = @Content)
            }
    )
    @PostMapping("settings")
    public ResponseEntity<AccountSubscriptionSettingsResponse> getContext(
            @Parameter(description = "Request body containing token and other settings parameters")
            @Valid @RequestBody AccountSubscriptionSettingsRequest request) {


        Map<AccountEntity, Boolean> accountSettingsInfo = accountService.findByAccessToken(request.getToken());
        if (!accountSettingsInfo.isEmpty()) {
            Map.Entry<AccountEntity, Boolean> entry = accountSettingsInfo.entrySet().iterator().next();
            AccountEntity accountEntity = entry.getKey();
            Boolean isNewAccessToken = entry.getValue();

            AccountSubscriptionSettingsResponse.Settings settings =
                    AccountSubscriptionSettingsMapper.INSTANCE.mapToAccountSubscriptionSettingsResponseSettings(accountEntity.getSettings());

            return ResponseEntity.ok(AccountSubscriptionSettingsResponse.builder()
                    .accessToken(accountEntity.getAccessToken())
                    .accountID(accountEntity.getAccountId())
                    .status(accountEntity.getActivated() && !accountEntity.getSuspended())
                    .settings(settings)
                    .isNewAccessToken(isNewAccessToken)
                    .build());
        }
        throw new ResourceNotFoundException("No record found for token: " + request.getToken());
    }
}
