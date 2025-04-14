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
        AppConfigEntity config = appConfigRepository.findByKey(contextKey);
        if (config == null) {
            return ResponseEntity.notFound().build();
        }

        // 🔧 Заглушка: просто вернём токен как будто это employee
        GetEmployeeContextResponse response = new GetEmployeeContextResponse();
        response.setToken(config.getValue());
        response.setFullName("Тестовый Сотрудник"); // позже заменим на реальный вызов MS

        return ResponseEntity.ok(response);
    }
}
