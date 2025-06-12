package com.siriuslab.onec.widget.domain.adapter.ms.api;

import com.siriuslab.onec.widget.app.component.JwtGenerator;
import com.siriuslab.onec.widget.domain.account.token.entity.AccountEntity;
import com.siriuslab.onec.widget.domain.account.token.service.AccountService;
import com.siriuslab.onec.widget.domain.adapter.ms.context.GetEmployeeContextResponse;
import com.siriuslab.onec.widget.domain.adapter.ms.dto.ProductDto;
import com.siriuslab.onec.widget.domain.adapter.ms.service.MsApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        String token = jwtGenerator.generateToken();
        jwtGenerator.verifyToken(token);

        GetEmployeeContextResponse response = msApiService.getContext(token, contextKey);

        AccountEntity accountEntity = accountService.findByAccountId(response.getAccountId());
        response.setToken(accountEntity.getAccessToken());
        response.setSettings(accountEntity.getSettings());

        return ResponseEntity.ok(response);
    }

    // ✅ Новый эндпоинт /products
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("products")
    public ResponseEntity<List<ProductDto>> getProducts(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7); // удаляем "Bearer "
        log.info("📦 Запрос продуктов с токеном: {}", token);

        List<ProductDto> products = msApiService.getProducts(token);
        return ResponseEntity.ok(products);
    }
}
