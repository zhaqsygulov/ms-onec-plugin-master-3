package com.siriuslab.onec.widget.domain.account.settings.mapper;

import com.siriuslab.onec.widget.domain.account.settings.dto.AccountSettingsUpdateRequest;
import com.siriuslab.onec.widget.domain.account.settings.entity.AccountSettingsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountSettingsMapper {
    AccountSettingsMapper INSTANCE = Mappers.getMapper(AccountSettingsMapper.class);

    
    
    AccountSettingsEntity mapToEntity(AccountSettingsUpdateRequest request);

    
    
    AccountSettingsUpdateRequest mapToRequest(AccountSettingsEntity entity);
}
