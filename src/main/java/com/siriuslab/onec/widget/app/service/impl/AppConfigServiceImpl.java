package com.siriuslab.onec.widget.app.service.impl;

import com.siriuslab.onec.widget.app.entity.app.config.AppConfigEntity;
import com.siriuslab.onec.widget.app.repository.AppConfigRepository;
import com.siriuslab.onec.widget.app.service.AppConfigService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppConfigServiceImpl implements AppConfigService {

    private final AppConfigRepository appConfigRepository;
    private final Map<String, String> configs = new HashMap<>();
    @PostConstruct
    private void loadConfigs() {
        appConfigRepository.findAll().forEach(config -> configs.put(config.getKey(), config.getValue()));
    }

    @Override
    public String getValueByKey(String key) {
        AppConfigEntity config = appConfigRepository.findByKey(key);
        return config != null ? config.getValue() : null;
    }
}
