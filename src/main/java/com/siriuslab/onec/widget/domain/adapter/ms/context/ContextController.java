package com.siriuslab.onec.widget.domain.adapter.ms.context;

import com.siriuslab.onec.widget.domain.account.config.entity.AppConfigEntity;
import com.siriuslab.onec.widget.domain.account.config.repository.AppConfigRepository;
import com.siriuslab.onec.widget.external.vendorapi.service.VendorApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app-ms-adapter/context")
@RequiredArgsConstructor
public class ContextController {

    private final AppConfigRepository appConfigRepository;
    private final VendorApiService vendorApiService;

    @GetMapping("/{contextKey}/employee")
    public ResponseEntity<GetEmployeeContextResponse> getEmployeeByContext(@PathVariable String contextKey) {
        AppConfigEntity config = appConfigRepository.findByContextKey(contextKey)
                .orElseThrow(() -> new RuntimeException("Context not found"));

        GetEmployeeContextResponse employee = vendorApiService.getEmployee(config.getAccessToken());
        return ResponseEntity.ok(employee);
    }
}
