package com.siriuslab.onec.widget.domain.adapter.ms.client;

import com.siriuslab.onec.widget.app.cofing.FeignConfig;
import com.siriuslab.onec.widget.domain.adapter.ms.context.GetEmployeeContextResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "msApiClient", url = "https://apps-api.moysklad.ru/api/vendor/1.0", configuration = FeignConfig.class)
public interface MsApiClient {
    @PostMapping(value = "/context/{context}")
    GetEmployeeContextResponse getContext(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String context
    );
}
