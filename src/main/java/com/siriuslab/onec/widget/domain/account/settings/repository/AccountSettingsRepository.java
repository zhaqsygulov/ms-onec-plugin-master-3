package com.siriuslab.onec.widget.domain.account.settings.repository;

import com.siriuslab.onec.widget.domain.account.settings.entity.AccountSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.siriuslab.onec.widget.domain.account.token.entity.AccountEntity;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;



@Repository
public interface AccountSettingsRepository extends JpaRepository<AccountSettingsEntity, Long> {
    Optional<AccountSettingsEntity> findByAccount(AccountEntity account);
}
