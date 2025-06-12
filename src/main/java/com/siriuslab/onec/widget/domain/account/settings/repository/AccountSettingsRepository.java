package com.siriuslab.onec.widget.domain.account.settings.repository;

import com.siriuslab.onec.widget.domain.account.settings.entity.AccountSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountSettingsRepository extends JpaRepository<AccountSettingsEntity, Long> {
}

