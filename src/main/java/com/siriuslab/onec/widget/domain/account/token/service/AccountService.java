package com.siriuslab.onec.widget.domain.account.token.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.siriuslab.onec.widget.domain.account.token.entity.AccountEntity;
import com.siriuslab.onec.widget.domain.activation.dto.deactivate.DeactivateAppRequest;

import java.util.Map;
import java.util.UUID;

public interface AccountService {
    AccountEntity findByAccountId(UUID id);
    public Map<AccountEntity, Boolean> findByAccessToken(String token);
    AccountEntity save(AccountEntity entity) throws JsonProcessingException;
    void uninstallOrSuspend(String appUid, String accountUid, DeactivateAppRequest request) throws JsonProcessingException;
}
