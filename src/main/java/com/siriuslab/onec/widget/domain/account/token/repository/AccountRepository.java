package com.siriuslab.onec.widget.domain.account.token.repository;

import com.siriuslab.onec.widget.domain.account.token.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByAccountId(UUID id);
    Optional<AccountEntity> findByAccessToken(String token);
}
