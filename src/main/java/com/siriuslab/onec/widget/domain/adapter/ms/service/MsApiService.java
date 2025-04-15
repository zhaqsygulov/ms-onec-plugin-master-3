package com.siriuslab.onec.widget.domain.adapter.ms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siriuslab.onec.widget.domain.adapter.ms.client.MsApiClient;
import com.siriuslab.onec.widget.domain.adapter.ms.context.GetEmployeeContextResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsApiService {

    private final MsApiClient msApiClient;
    private final ObjectMapper objectMapper;

    public GetEmployeeContextResponse getContext(String accessToken, String contextKey) {
        try {
            String bearerToken = "Bearer " + accessToken;
            GetEmployeeContextResponse response = msApiClient.getContext(bearerToken, contextKey);
            log.info("✅ Контекст сотрудника: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
            return response;
        } catch (HttpClientErrorException.NotFound e) {
            log.error("❌ Контекст не найден: {}", e.getMessage());
            throw new IllegalArgumentException("Контекст не найден.");
        } catch (JsonProcessingException e) {
            log.error("❌ Ошибка обработки JSON: {}", e.getMessage());
            throw new RuntimeException("Ошибка при обработке JSON.", e);
        } catch (Exception e) {
            log.error("❌ Неожиданная ошибка: {}", e.getMessage());
            throw new RuntimeException("Произошла непредвиденная ошибка.", e);
        }
    }
}
