package com.siriuslab.onec.widget.domain.account.deactivation.repository;

import com.siriuslab.onec.widget.domain.account.deactivation.entity.DeactivationHistoryEntity;
import com.siriuslab.onec.widget.domain.activation.entity.ActivationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeactivationHistoryRepository extends JpaRepository<DeactivationHistoryEntity, Long> {
    boolean existsByAccountId(UUID accountId);
    Optional<DeactivationHistoryEntity> findByAccountId(UUID accountId);

    Optional<DeactivationHistoryEntity> findFirstByOldAccessTokenOrderByCreatedAtDesc(String oldAccessToken);
}
