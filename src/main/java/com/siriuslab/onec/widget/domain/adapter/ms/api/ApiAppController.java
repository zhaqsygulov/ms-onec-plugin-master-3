package com.siriuslab.onec.widget.domain.adapter.ms.api;

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
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/app-ms-adapter/")
@RequiredArgsConstructor
public class ApiAppController {

    private final JwtGenerator jwtGenerator;
    private final MsApiService msApiService;
    private final AccountService accountService;

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @CrossOrigin(origins = "*") // или указать фронт: https://opt-order-frontend-9m7d.vercel.app
    @RequestMapping(
        value = "context/{contextKey}/employee",
        method = {RequestMethod.GET, RequestMethod.OPTIONS}
    )
    public ResponseEntity<GetEmployeeContextResponse> getContext(@PathVariable String contextKey) {
        log.info("Received contextKey: {}", contextKey);

        String token = jwtGenerator.generateToken();
        jwtGenerator.verifyToken(token);

        GetEmployeeContextResponse response = msApiService.getContext(token, contextKey);

        AccountEntity accountEntity = accountService.findByAccountId(response.getAccountId());
        response.setToken(accountEntity.getAccessToken());
        response.setSettings(accountEntity.getSettings());

        return ResponseEntity.ok(response);
    }
}
