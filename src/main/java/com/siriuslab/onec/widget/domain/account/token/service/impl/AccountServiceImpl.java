package com.siriuslab.onec.widget.domain.account.token.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siriuslab.onec.widget.app.entity.ResourceNotFoundException;
import com.siriuslab.onec.widget.domain.account.deactivation.entity.DeactivationHistoryEntity;
import com.siriuslab.onec.widget.domain.account.deactivation.repository.DeactivationHistoryRepository;
import com.siriuslab.onec.widget.domain.account.token.entity.AccountEntity;
import com.siriuslab.onec.widget.domain.account.token.repository.AccountRepository;
import com.siriuslab.onec.widget.domain.account.token.service.AccountService;
import com.siriuslab.onec.widget.domain.activation.dto.deactivate.DeactivateAppRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final DeactivationHistoryRepository deactivationHistoryRepository;
    private final ObjectMapper objectMapper;

    @Override
    public AccountEntity findByAccountId(UUID id) throws ResourceNotFoundException {
        return repository
                .findByAccountId(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserTokenEntity not found by account id : " + id));
    }

    @Override
    public Map<AccountEntity, Boolean> findByAccessToken(String token) {
        // Попробуйте найти сущность Account по предоставленному токену
        return repository.findByAccessToken(token)
                .map(accountEntity -> Collections.singletonMap(accountEntity, false)) // Если найдено, возвращаем с isNewToken = false
                .orElseGet(() -> {
                    // Если не найдено, проверяем историю деактивации по старому токену
                    return deactivationHistoryRepository.findFirstByOldAccessTokenOrderByCreatedAtDesc(token)
                            .map(deactivationHistory -> {
                                // Если найдено в истории деактивации, получаем новый токен по accountId
                                UUID accountId = deactivationHistory.getAccountId();
                                AccountEntity accountEntity = repository.findByAccountId(accountId)
                                        .orElseThrow(() -> new ResourceNotFoundException("AccountEntity not found by accountId: " + accountId));
                                return Collections.singletonMap(accountEntity, true); // Возвращаем с isNewToken = true
                            })
                            .orElseThrow(() -> new ResourceNotFoundException("No record found for token: " + token));
                });
    }

    @Override
    public AccountEntity save(AccountEntity entity) throws JsonProcessingException {
        return (entity.getAccountId() != null && repository.findByAccountId(entity.getAccountId()).isPresent())
                ? update(entity)
                : create(entity);
    }

    @Override
    @Transactional
    public void uninstallOrSuspend(String appUid, String accountUid, DeactivateAppRequest request) throws JsonProcessingException {
        Optional<AccountEntity> accountEntityOptional = repository
                .findByAccountId(UUID.fromString(accountUid));

        if (accountEntityOptional.isPresent()) {
            AccountEntity account = accountEntityOptional.get();
            account.setActivated(false);
            account.setSuspended(true);
            update(account);

            DeactivationHistoryEntity deactivationHistory = new DeactivationHistoryEntity();
            deactivationHistory.setAccountName(request.getAccountName());
            deactivationHistory.setCause(request.getCause().getValue());
            deactivationHistory.setActivation(account.getActivation());
            deactivationHistory.setAccountId(UUID.fromString(accountUid));
            deactivationHistory.setOldAccessToken(account.getAccessToken());
            deactivationHistoryRepository.save(deactivationHistory);
        }
    }

    private AccountEntity create(AccountEntity entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    private AccountEntity update(AccountEntity entity) throws JsonProcessingException {
        if (entity.getAccountId() == null) {
            throw new IllegalArgumentException("AccountId must be provided for updates");
        }
        AccountEntity existingEntity = repository.findByAccountId(entity.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with accountId: " + entity.getAccountId()));

        existingEntity.setAccountId(entity.getAccountId());
        existingEntity.setActivated(entity.getActivated());
        existingEntity.setSuspended(entity.getSuspended());
        existingEntity.setAccessToken(entity.getAccessToken());

        log.info("UPDATED UserTokenEntity with id : {} body : {}",
                entity.getAccountId(),
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(existingEntity));

        return repository.save(existingEntity);
    }
}
