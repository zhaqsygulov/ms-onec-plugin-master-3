package com.siriuslab.onec.widget.domain.account.settings.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.siriuslab.onec.widget.domain.account.settings.entity.AccountSettingsEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AccountSettingsService {
    AccountSettingsEntity getByAccountId(UUID id);
    AccountSettingsEntity save(UUID accountId, AccountSettingsEntity entity) throws JsonProcessingException;

    void saveClientSettings(
            UUID accountId,
            String name,
            String desc,
            String address,
            double minSum,
            String whatsapp,
            String telegram,
            String gis2,
            MultipartFile logo
    );
}
