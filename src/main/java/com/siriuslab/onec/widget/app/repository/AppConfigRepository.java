package com.siriuslab.onec.widget.app.repository;

import com.siriuslab.onec.widget.app.entity.app.config.AppConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfigRepository extends JpaRepository<AppConfigEntity, Long> {
    AppConfigEntity findByKey(String key);
}
