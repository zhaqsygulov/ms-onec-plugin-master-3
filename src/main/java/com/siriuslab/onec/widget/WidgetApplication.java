package com.siriuslab.onec.widget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
public class WidgetApplication {
	public static void main(String[] args) {
		SpringApplication.run(WidgetApplication.class, args);
	}
}
