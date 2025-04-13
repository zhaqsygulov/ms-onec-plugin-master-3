package com.siriuslab.onec.widget.domain.account.settings.service.impl;

import com.siriuslab.onec.widget.domain.account.settings.dto.AccountSettingsRequest;
import com.siriuslab.onec.widget.domain.account.settings.dto.AccountSettingsResponse;
import com.siriuslab.onec.widget.domain.account.settings.entity.AccountSettingsEntity;
import com.siriuslab.onec.widget.domain.account.settings.repository.AccountSettingsRepository;
import com.siriuslab.onec.widget.domain.account.settings.service.AccountSettingsService;
import com.siriuslab.onec.widget.domain.account.token.entity.AccountEntity;
import com.siriuslab.onec.widget.domain.account.token.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountSettingsServiceImpl implements AccountSettingsService {

    private final AccountSettingsRepository settingsRepository;
    private final AccountRepository accountRepository;

    @Override
    public AccountSettingsResponse getSettings(UUID accountId) {
        AccountEntity account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        AccountSettingsEntity settings = settingsRepository.findByAccount(account)
                .orElse(new AccountSettingsEntity());

        return AccountSettingsResponse.builder()
                .companyName(settings.getCompanyName())
                .description(settings.getCompanyDescription())
                .address(settings.getCompanyAddress())
                .minOrderSum(settings.getMinOrderSum())
                .whatsapp(settings.getWhatsappUrl())
                .telegram(settings.getTelegramUrl())
                .gis2(settings.getGis2Url())
                .logo(settings.getLogoPath())
                .build();
    }

    @Override
    public void updateSettings(UUID accountId, AccountSettingsRequest request) {
        AccountEntity account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        AccountSettingsEntity settings = settingsRepository.findByAccount(account)
                .orElse(new AccountSettingsEntity());

        settings.setAccount(account);
        settings.setCompanyName(request.getCompanyName());
        settings.setCompanyDescription(request.getDescription());
        settings.setCompanyAddress(request.getAddress());
        settings.setMinOrderSum(request.getMinOrderSum());
        settings.setWhatsappUrl(request.getWhatsapp());
        settings.setTelegramUrl(request.getTelegram());
        settings.setGis2Url(request.getGis2());

        // TODO: Обработка лого, если появится
        if (request.getLogoPath() != null) {
            settings.setLogoPath(request.getLogoPath());
        }

        settingsRepository.save(settings);
        log.info("Account settings updated for accountId={}", accountId);
    }
}
