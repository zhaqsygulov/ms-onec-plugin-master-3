package com.siriuslab.onec.widget.domain.activation.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siriuslab.onec.widget.app.entity.ResourceNotFoundException;
import com.siriuslab.onec.widget.domain.activation.entity.ActivationEntity;
import com.siriuslab.onec.widget.domain.activation.repository.ActivationRepository;
import com.siriuslab.onec.widget.domain.activation.service.ActivationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivationServiceImpl implements ActivationService {

    private final ActivationRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public ActivationEntity save(ActivationEntity entity) throws JsonProcessingException {
        return (entity.getAccountId() != null && repository.findByAccountId(entity.getAccountId()).isPresent())
                ? update(entity)
                : create(entity);
    }

    @Override
    public Optional<ActivationEntity> findByAccountId(UUID id) {
        return repository.findByAccountId(id);
    }

    @Override
    public Optional<ActivationEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private ActivationEntity create(ActivationEntity entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    private ActivationEntity update(ActivationEntity entity) throws JsonProcessingException {
        if (entity.getAccountId() == null) {
            throw new IllegalArgumentException("AccountId must be provided for updates");
        }
        ActivationEntity existingEntity = repository.findByAccountId(entity.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with accountId: " + entity.getAccountId()));

        existingEntity.setAppUid(entity.getAppUid());
        existingEntity.setAccountId(entity.getAccountId());
        existingEntity.setAccountName(entity.getAccountName());
        existingEntity.setCause(entity.getCause());
        existingEntity.setTariffId(entity.getTariffId());
        existingEntity.setTrial(entity.getTrial());
        existingEntity.setTariffName(entity.getTariffName());
        existingEntity.setExpiryMoment(entity.getExpiryMoment());
        existingEntity.setNotForResale(entity.getNotForResale());
        existingEntity.setPartner(entity.getPartner());

        log.info("UPDATED UserTokenEntity with id : {} body : {}",
                entity.getAccountId(),
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(existingEntity));

        return repository.save(existingEntity);
    }
}
