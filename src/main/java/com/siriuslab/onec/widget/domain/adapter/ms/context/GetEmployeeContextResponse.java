package com.siriuslab.onec.widget.domain.adapter.ms.context;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.siriuslab.onec.widget.domain.account.settings.entity.AccountSettingsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetEmployeeContextResponse {
    private UUID id;
    private UUID accountId;
    private boolean shared;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime updatedAt;
    private String name;
    private String externalCode;
    private boolean archived;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createdAt;
    private String uid;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String shortFio;
    private String token;
    private AccountSettingsEntity settings;
}
