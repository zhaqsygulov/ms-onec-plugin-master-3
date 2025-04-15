package com.siriuslab.onec.widget.app.cofing;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // Включает полный лог всех запросов/ответов
    }

    // Если нужно глобально добавить заголовки:
    // @Bean
    // public RequestInterceptor requestInterceptor() {
    //     return template -> template.header("Content-Type", "application/json");
    // }

}
