package com.siriuslab.onec.widget.domain.account.settings.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountSettingsResponse {
    private String companyName;
    private String description;
    private String address;
    private Double minOrderSum;
    private String whatsapp;
    private String telegram;
    private String gis2;
    private String logo;
}
