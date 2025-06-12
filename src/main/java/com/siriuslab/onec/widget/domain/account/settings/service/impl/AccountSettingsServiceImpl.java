package com.siriuslab.onec.widget.domain.account.settings.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siriuslab.onec.widget.app.entity.ResourceNotFoundException;
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

    private final AccountSettingsRepository accountSettingsRepository;
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;

    @Override
    public AccountSettingsEntity getByAccountId(UUID id) {
         AccountEntity account = accountRepository.findByAccountId(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Account not found with accountId: " + id));

         if (account.getSettings() == null){
             throw new ResourceNotFoundException("Account settings not found with accountId: " + id);
         }
        return account.getSettings();
    }

    @Override
    public AccountSettingsEntity save(UUID accountId, AccountSettingsEntity settings) throws JsonProcessingException {
        log.info("here");
        AccountEntity account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id : " + accountId + " not found for save or update account settings"));

        log.info("account.getSettings()  : {}", account.getSettings());
        return (account.getSettings() == null)
                ? create(account, settings)
                : update(account, settings);
    }

    private AccountSettingsEntity create(AccountEntity account, AccountSettingsEntity settings) {
        settings.setId(account.getId());
        settings.setAccount(account);
        return accountSettingsRepository.save(settings);
    }

    private AccountSettingsEntity update(AccountEntity account, AccountSettingsEntity entity) throws JsonProcessingException {

        if (account.getId() == null) {
            throw new IllegalArgumentException("Id must be provided for updates");
        }

        AccountSettingsEntity existingEntity = accountSettingsRepository.findById(account.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with accountId: " + entity.getId()));

        log.info("BEFORE AccountSettingsEntity with id : {} body : {}",
                account.getId(),
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(existingEntity));

        existingEntity.setId(account.getId());

        existingEntity.setCustomerOrder(entity.isCustomerOrder());
        existingEntity.setDemand(entity.isDemand());
        existingEntity.setDemandIn(entity.isDemandIn());
        existingEntity.setSalesReturn(entity.isSalesReturn());
        existingEntity.setSalesReturnIn(entity.isSalesReturnIn());
        existingEntity.setSupply(entity.isSupply());
        existingEntity.setSupplyIn(entity.isSupplyIn());
        existingEntity.setPurchaseReturn(entity.isPurchaseReturn());
        existingEntity.setEnter(entity.isEnter());
        existingEntity.setEnterIn(entity.isEnterIn());
        existingEntity.setPaymentIn(entity.isPaymentIn());
        existingEntity.setPaymentInIn(entity.isPaymentInIn());
        existingEntity.setPaymentOut(entity.isPaymentOut());
        existingEntity.setPaymentOutIn(entity.isPaymentOutIn());
        existingEntity.setCashIn(entity.isCashIn());
        existingEntity.setCashInIn(entity.isCashInIn());
        existingEntity.setCashOut(entity.isCashOut());
        existingEntity.setCashOutIn(entity.isCashOutIn());
        existingEntity.setPurchaseOrder(entity.isPurchaseOrder());
        existingEntity.setLoss(entity.isLoss());
        existingEntity.setMove(entity.isMove());
        existingEntity.setProcessing(entity.isProcessing());
        existingEntity.setGood(entity.isGood());
        existingEntity.setGoodIn(entity.isGoodIn());
        existingEntity.setCompany(entity.isCompany());
        existingEntity.setCompanyIn(entity.isCompanyIn());
        existingEntity.setContract(entity.isContract());
        existingEntity.setContractIn(entity.isContractIn());
        existingEntity.setProcessingPlan(entity.isProcessingPlan());
        existingEntity.setBundle(entity.isBundle());
        existingEntity.setProductFolder(entity.isProductFolder());
        existingEntity.setProductFolderIn(entity.isProductFolderIn());

        existingEntity.setManually(entity.isManually());
        existingEntity.setProductionTask(entity.isProductionTask());
        existingEntity.setRetailDemand(entity.isRetailDemand());
        existingEntity.setRetailSalesReturn(entity.isRetailSalesReturn());
        existingEntity.setFactureOut(entity.isFactureOut());
        existingEntity.setAbstractInventory(entity.isAbstractInventory());

        AccountSettingsEntity updatedEntity = accountSettingsRepository.save(existingEntity);
        log.info("SAVED AccountSettingsEntity with id : {} body : {}",
                updatedEntity.getId(),
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(updatedEntity));

        return updatedEntity;
    }
}
