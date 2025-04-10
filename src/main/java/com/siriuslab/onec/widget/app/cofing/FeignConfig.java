package com.siriuslab.onec.widget.app.cofing;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor gzipRequestInterceptor() {
        return template -> {
            try {
                template.header("Accept-Encoding", "gzip");
                template.header("Content-Type", "application/json");
                template.header("Accept", "application/json");
            } catch (Exception e) {
                log.error("Error configuring Feign headers: {}", e.getMessage());
            }
        };
    }
}