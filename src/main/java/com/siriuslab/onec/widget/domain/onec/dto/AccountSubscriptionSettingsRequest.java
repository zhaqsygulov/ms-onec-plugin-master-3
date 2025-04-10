package com.siriuslab.onec.widget.domain.onec.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Schema(description = "Request object for retrieving account subscription settings")
public class AccountSubscriptionSettingsRequest {

    @NotNull
    @NotEmpty
    @Schema(description = "Token used to authenticate the request", example = "90f8280ea5a2a618246fb761ae9665e13c0829d5")
    private String token;
}