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
            GetEmployeeContextResponse response = msApiClient.getContext("Bearer " + bearerToken, context);
            log.info("applicationActivationEntity : {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
            return response;
        } catch (HttpClientErrorException.NotFound e) {
            log.error("404 Not Found: {}", e.getMessage());
            throw new IllegalArgumentException("The requested context was not found.");
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            throw new RuntimeException("Error processing JSON response.", e);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            throw new RuntimeException("An unexpected error occurred.", e);
        }
    }
}
