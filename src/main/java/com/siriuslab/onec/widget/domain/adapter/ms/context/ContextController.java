package com.siriuslab.onec.widget.controller;

import com.siriuslab.onec.widget.app.entity.app.config.AppConfigEntity;
import com.siriuslab.onec.widget.app.repository.AppConfigRepository;
import com.siriuslab.onec.widget.domain.adapter.ms.context.GetEmployeeContextResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app-ms-adapter/context")
@RequiredArgsConstructor
public class ContextController {

    private final AppConfigRepository appConfigRepository;

    @GetMapping("/{contextKey}/employee")
    public ResponseEntity<GetEmployeeContextResponse> getEmployeeByContext(@PathVariable String contextKey) {
        AppConfigEntity config = appConfigRepository.findByContextKey(contextKey)
                .orElseThrow(() -> new RuntimeException("Context not found"));

        // 👇 Здесь заглушка — нужно заменить на вызов к API МоегоСклада по токену
        GetEmployeeContextResponse response = new GetEmployeeContextResponse();
        response.setToken(config.getAccessToken());
        response.setFullName("Заглушка Сотрудника");

        return ResponseEntity.ok(response);
    }
}
