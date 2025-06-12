package com.siriuslab.onec.widget.domain.adapter.ms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siriuslab.onec.widget.domain.adapter.ms.client.MsApiClient;
import com.siriuslab.onec.widget.domain.adapter.ms.context.GetEmployeeContextResponse;
import com.siriuslab.onec.widget.domain.adapter.ms.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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

    // ✅ Новый метод — получить список товаров
    public List<ProductDto> getProducts(String accessToken) {
        try {
            String url = "https://online.moysklad.ru/api/remap/1.2/entity/product";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<JsonNode> response = new RestTemplate().exchange(
                url, HttpMethod.GET, requestEntity, JsonNode.class
            );

            JsonNode rows = response.getBody().get("rows");
            List<ProductDto> products = new ArrayList<>();

            for (JsonNode item : rows) {
                String id = item.get("id").asText();
                String name = item.get("name").asText();
                String description = item.path("description").asText("");
                double price = item.path("salePrices").isArray() && item.get("salePrices").size() > 0
                        ? item.get("salePrices").get(0).get("value").asDouble() / 100
                        : 0.0;
                double quantity = item.path("quantity").asDouble(0.0);

                products.add(new ProductDto(id, name, description, price, quantity));
            }

            return products;

        } catch (Exception e) {
            log.error("❌ Ошибка при получении товаров из МоегоСклада: {}", e.getMessage());
            throw new RuntimeException("Не удалось получить товары", e);
        }
    }
}
