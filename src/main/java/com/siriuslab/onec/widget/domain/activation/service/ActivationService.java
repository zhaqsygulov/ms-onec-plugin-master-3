package com.siriuslab.onec.widget.domain.activation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.siriuslab.onec.widget.domain.activation.entity.ActivationEntity;

import java.util.Optional;
import java.util.UUID;

public interface ActivationService {
    ActivationEntity save(ActivationEntity entity) throws JsonProcessingException;
    Optional<ActivationEntity> findByAccountId(UUID id);
    Optional<ActivationEntity> findById(Long id);
    void deleteById(Long id);
}
