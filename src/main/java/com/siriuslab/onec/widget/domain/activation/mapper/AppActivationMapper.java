package com.siriuslab.onec.widget.domain.activation.mapper;

import com.siriuslab.onec.widget.domain.activation.dto.activate.AppActivationRequest;
import com.siriuslab.onec.widget.domain.activation.entity.ActivationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppActivationMapper {
    AppActivationMapper INSTANCE = Mappers.getMapper(AppActivationMapper.class);

    @Mapping(source = "subscription.tariffId", target = "tariffId")
    @Mapping(source = "subscription.trial", target = "trial")
    @Mapping(source = "subscription.tariffName", target = "tariffName")
    @Mapping(source = "subscription.expiryMoment", target = "expiryMoment")
    @Mapping(source = "subscription.notForResale", target = "notForResale")
    @Mapping(source = "subscription.partner", target = "partner")
    ActivationEntity mapToEntity(AppActivationRequest request);

    // You can also add a reverse mapping if needed
    @Mapping(source = "tariffId", target = "subscription.tariffId")
    @Mapping(source = "trial", target = "subscription.trial")
    @Mapping(source = "tariffName", target = "subscription.tariffName")
    @Mapping(source = "expiryMoment", target = "subscription.expiryMoment")
    @Mapping(source = "notForResale", target = "subscription.notForResale")
    @Mapping(source = "partner", target = "subscription.partner")
    AppActivationRequest mapToRequest(ActivationEntity entity);
}
