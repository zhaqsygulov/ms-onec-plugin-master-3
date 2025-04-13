package com.siriuslab.onec.widget.domain.account.settings.service.impl;

import com.siriuslab.onec.widget.domain.account.settings.entity.AccountSettingsEntity;
import com.siriuslab.onec.widget.domain.account.settings.repository.AccountSettingsRepository;
import com.siriuslab.onec.widget.domain.account.settings.service.AccountSettingsService;
import com.siriuslab.onec.widget.domain.account.token.entity.AccountEntity;
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
    public AccountSettingsEntity getByAccountId(UUID id) {
        return settingsRepository.findById(id.getMostSignificantBits()) // пример ID-адаптации
                .orElseThrow(() -> new RuntimeException("Settings not found"));
    }

    @Override
    public AccountSettingsEntity save(UUID accountId, AccountSettingsEntity entity) {
        return settingsRepository.save(entity);
    }

    @Override
    public void saveClientSettings(UUID accountId,
                                   String name,
                                   String desc,
                                   String address,
                                   double minSum,
                                   String whatsapp,
                                   String telegram,
                                   String gis2,
                                   MultipartFile logo) {
        AccountEntity account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Optional<AccountSettingsEntity> optional = settingsRepository.findByAccount(account);
        AccountSettingsEntity settings = optional.orElse(new AccountSettingsEntity());

        settings.setAccount(account);
        settings.setCompanyName(name);
        settings.setCompanyDescription(desc);
        settings.setCompanyAddress(address);
        settings.setMinOrderSum(minSum);
        settings.setWhatsappUrl(whatsapp);
        settings.setTelegramUrl(telegram);
        settings.setGis2Url(gis2);

        if (logo != null && !logo.isEmpty()) {
            settings.setLogoPath("/logo/" + logo.getOriginalFilename()); // или логика загрузки файла
        }

        settingsRepository.save(settings);
        log.info("Client settings saved for accountId={}", accountId);
    }
}
