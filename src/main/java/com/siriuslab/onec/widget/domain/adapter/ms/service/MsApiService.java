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

    public GetEmployeeContextResponse getContext(String bearerToken, String context) throws JsonProcessingException {
        try {
            String authHeader = "Bearer " + bearerToken;
            log.info("🟡 Calling MS API with contextKey={} and token={}", context, bearerToken);

            GetEmployeeContextResponse response = msApiClient.getContext(authHeader, context);

            log.info("✅ Получен ответ от MS API:\n{}", 
                     objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));

            return response;

        } catch (HttpClientErrorException.NotFound e) {
            log.error("❌ 404 Not Found for context '{}': {}", context, e.getMessage());
            throw new IllegalArgumentException("Контекст не найден.");
        } catch (JsonProcessingException e) {
            log.error("❌ JSON processing error: {}", e.getMessage());
            throw new RuntimeException("Ошибка обработки JSON", e);
        } catch (Exception e) {
            log.error("❌ Unexpected error: {}", e.getMessage(), e);
            throw new RuntimeException("Непредвиденная ошибка при вызове MS API", e);
        }
    }
}
