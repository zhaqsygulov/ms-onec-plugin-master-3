package com.siriuslab.onec.widget.domain.onec.mapper;

import com.siriuslab.onec.widget.domain.account.settings.entity.AccountSettingsEntity;
import com.siriuslab.onec.widget.domain.onec.dto.AccountSubscriptionSettingsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountSubscriptionSettingsMapper {
    AccountSubscriptionSettingsMapper INSTANCE = Mappers.getMapper(AccountSubscriptionSettingsMapper.class);

    @Mapping(target = "updateDate", source = "updatedAt")
    AccountSubscriptionSettingsResponse.Settings mapToAccountSubscriptionSettingsResponseSettings(AccountSettingsEntity request);
}
