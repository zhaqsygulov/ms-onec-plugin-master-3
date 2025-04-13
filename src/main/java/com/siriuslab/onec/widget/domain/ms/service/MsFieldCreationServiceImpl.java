package com.siriuslab.onec.widget.domain.ms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MsFieldCreationServiceImpl implements MsFieldCreationService {

    private final RestTemplate restTemplate;

    @Override
    public void createProductFields(String token) {
        createField("product", "Шт в упаковке", "pack_qty", "long", token);
        createField("product", "Упаковок в коробке", "box_qty", "long", token);
        createField("product", "Мин. шт для заказа", "min_qty", "long", token);
    }

    @Override
    public void createOrderFields(String token) {
        createField("customerorder", "Ссылка на накладную", "doc_link", "text", token);
        createField("customerorder", "Тип оплаты", "payment_type", "text", token);
    }

    private void createField(String entity, String name, String code, String type, String token) {
        String url = "https://api.moysklad.ru/api/remap/1.2/entity/" + entity + "/metadata/attributes";
        Map<String, Object> body = Map.of(
                "name", name,
                "type", type,
                "required", false,
                "code", code
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.postForEntity(url, request, String.class);
    }
}
