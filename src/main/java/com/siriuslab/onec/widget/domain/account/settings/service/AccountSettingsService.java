package com.siriuslab.onec.widget.domain.account.settings.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.siriuslab.onec.widget.domain.account.settings.entity.AccountSettingsEntity;

import java.util.UUID;

public interface AccountSettingsService {
    AccountSettingsEntity getByAccountId(UUID id);
    AccountSettingsEntity save(UUID accountId, AccountSettingsEntity entity) throws JsonProcessingException;
}
