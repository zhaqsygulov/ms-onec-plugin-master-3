package com.siriuslab.onec.widget.domain.activation.repository;

import com.siriuslab.onec.widget.domain.activation.entity.ActivationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ActivationRepository extends JpaRepository<ActivationEntity, Long> {
    boolean existsByAccountId(UUID accountId);
    Optional<ActivationEntity> findByAccountId(UUID accountId);
}
