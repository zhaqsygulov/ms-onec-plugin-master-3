package com.siriuslab.onec.widget.domain.adapter.ms.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.siriuslab.onec.widget.app.component.JwtGenerator;
import com.siriuslab.onec.widget.domain.account.token.entity.AccountEntity;
import com.siriuslab.onec.widget.domain.account.token.service.AccountService;
import com.siriuslab.onec.widget.domain.adapter.ms.context.GetEmployeeContextResponse;
import com.siriuslab.onec.widget.domain.adapter.ms.service.MsApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/app-ms-adapter/")
@RequiredArgsConstructor
public class ApiAppController {

    private final JwtGenerator jwtGenerator;
    private final MsApiService msApiService;
    private final AccountService accountService;

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping("context/{contextKey}/employee")
    public ResponseEntity<GetEmployeeContextResponse> getContext(@PathVariable String contextKey) {
        log.info("Received contextKey: {}", contextKey);
        // TODO move business logic into service
        String token = jwtGenerator.generateToken();
        jwtGenerator.verifyToken(token);
        GetEmployeeContextResponse response;
        try {
            response = msApiService.getContext(token, contextKey);
        } catch (JsonProcessingException e) {
            log.error("msApiService.getContext error by token : {}  context : {} error : {}",
                    token, contextKey, e.getMessage());
            throw new IllegalArgumentException(e);
        }

        AccountEntity accountEntity = accountService.findByAccountId(response.getAccountId());
        response.setToken(accountEntity.getAccessToken());
        response.setSettings(accountEntity.getSettings());
        return ResponseEntity.ok(response);
    }
}
