package com.siriuslab.onec.widget.domain.account.settings.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.siriuslab.onec.widget.domain.account.settings.entity.AccountSettingsEntity;
import com.siriuslab.onec.widget.domain.account.settings.repository.AccountSettingsRepository;
import com.siriuslab.onec.widget.domain.account.settings.service.AccountSettingsService;
import com.siriuslab.onec.widget.domain.account.token.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountSettingsServiceImpl implements AccountSettingsService {

    private final AccountSettingsRepository settingsRepository;
    private final AccountRepository accountRepository;

    @Override
    public AccountSettingsEntity getByAccountId(UUID accountId) {
        return settingsRepository.findByAccountId(accountId)
                .orElseGet(() -> {
                    AccountSettingsEntity empty = new AccountSettingsEntity();
                    empty.setAccountId(accountId);
                    return settingsRepository.save(empty);
                });
    }

    @Override
    public AccountSettingsEntity save(UUID accountId, AccountSettingsEntity entity) throws JsonProcessingException {
        entity.setAccountId(accountId);
        return settingsRepository.save(entity);
    }

    @Override
    public void saveClientSettings(UUID accountId, String name, String desc, String address, double minSum,
                                   String whatsapp, String telegram, String gis2, MultipartFile logo) {

        Optional<AccountSettingsEntity> settingsOpt = settingsRepository.findByAccountId(accountId);

        AccountSettingsEntity settings = settingsOpt.orElseGet(() -> {
            AccountSettingsEntity s = new AccountSettingsEntity();
            s.setAccountId(accountId);
            return s;
        });

        settings.setCompanyName(name);
        settings.setDescription(desc);
        settings.setAddress(address);
        settings.setMinOrderSum(minSum);
        settings.setWhatsapp(whatsapp);
        settings.setTelegram(telegram);
        settings.setGis2(gis2);

        if (logo != null && !logo.isEmpty()) {
            try {
                settings.setLogo(logo.getBytes());
            } catch (Exception e) {
                log.warn("Ошибка при загрузке логотипа: {}", e.getMessage());
            }
        }

        settingsRepository.save(settings);
        log.info("Настройки клиента сохранены для accountId={}", accountId);
    }
}
