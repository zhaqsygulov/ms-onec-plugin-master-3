package com.siriuslab.onec.widget.domain.onec.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Schema(description = "Response object for account subscription settings")
public class AccountSubscriptionSettingsResponse {

    @Schema(description = "The unique identifier of the account", example = "123456")
    @JsonProperty("accountID")
    private UUID accountID;

    @Schema(description = "The access token used for authentication", example = "90f8280ea5a2a618246fb761ae9665e13c0829d5")
    @JsonProperty("access_token")
    private String accessToken;

    @Schema(description = "Status of the subscription", example = "true")
    @JsonProperty("isNewAccessToken")
    private boolean isNewAccessToken;

    @Schema(description = "Status of the subscription", example = "true")
    @JsonProperty("status")
    private boolean status;


    @Schema(description = "Detailed settings for the subscription")
    @JsonProperty("settings")
    private Settings settings;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Jacksonized
    @Schema(description = "Detailed settings for the subscription")
    public static class Settings {

        @Schema(description = "Indicates whether customerOrder are enabled", example = "true")
        @JsonProperty("customerOrder")
        private boolean customerOrder;

        @Schema(description = "Indicates whether demand are enabled", example = "true")
        @JsonProperty("demand")
        private boolean demand;

        @Schema(description = "Indicates whether demandIn are enabled", example = "true")
        @JsonProperty("demandIn")
        private boolean demandIn;

        @Schema(description = "Indicates whether salesReturn are enabled", example = "true")
        @JsonProperty("salesReturn")
        private boolean salesReturn;

        @Schema(description = "Indicates whether salesReturnIn are enabled", example = "true")
        @JsonProperty("salesReturnIn")
        private boolean salesReturnIn;

        @Schema(description = "Indicates whether supply are enabled", example = "true")
        @JsonProperty("supply")
        private boolean supply;

        @Schema(description = "Indicates whether supplyIn are enabled", example = "true")
        @JsonProperty("supplyIn")
        private boolean supplyIn;

        @Schema(description = "Indicates whether purchaseReturn are enabled", example = "true")
        @JsonProperty("purchaseReturn")
        private boolean purchaseReturn;

        @Schema(description = "Indicates whether enter are enabled", example = "true")
        @JsonProperty("enter")
        private boolean enter;

        @Schema(description = "Indicates whether enterIn are enabled", example = "true")
        @JsonProperty("enterIn")
        private boolean enterIn;

        @Schema(description = "Indicates whether paymentIn are enabled", example = "true")
        @JsonProperty("paymentIn")
        private boolean paymentIn;

        @Schema(description = "Indicates whether paymentInIn are enabled", example = "true")
        @JsonProperty("paymentInIn")
        private boolean paymentInIn;

        @Schema(description = "Indicates whether paymentOut are enabled", example = "true")
        @JsonProperty("paymentOut")
        private boolean paymentOut;

        @Schema(description = "Indicates whether paymentOutIn are enabled", example = "true")
        @JsonProperty("paymentOutIn")
        private boolean paymentOutIn;

        @Schema(description = "Indicates whether cashIn are enabled", example = "true")
        @JsonProperty("cashIn")
        private boolean cashIn;

        @Schema(description = "Indicates whether cashInIn are enabled", example = "true")
        @JsonProperty("cashInIn")
        private boolean cashInIn;

        @Schema(description = "Indicates whether cashOut are enabled", example = "true")
        @JsonProperty("cashOut")
        private boolean cashOut;

        @Schema(description = "Indicates whether cashOutIn are enabled", example = "true")
        @JsonProperty("cashOutIn")
        private boolean cashOutIn;

        @Schema(description = "Indicates whether purchaseOrder are enabled", example = "true")
        @JsonProperty("purchaseOrder")
        private boolean purchaseOrder;

        @Schema(description = "Indicates whether loss are enabled", example = "true")
        @JsonProperty("loss")
        private boolean loss;

        @Schema(description = "Indicates whether move are enabled", example = "true")
        @JsonProperty("move")
        private boolean move;

        @Schema(description = "Indicates whether processing are enabled", example = "true")
        @JsonProperty("processing")
        private boolean processing;

        /**
         * reference
         */

        @Schema(description = "Indicates whether reference good are enabled", example = "true")
        @JsonProperty("good")
        private boolean good;

        @Schema(description = "Indicates whether goodIn are enabled", example = "true")
        @JsonProperty("goodIn")
        private boolean goodIn;

        @Schema(description = "Indicates whether company are enabled", example = "true")
        @JsonProperty("company")
        private boolean company;

        @Schema(description = "Indicates whether companyIn are enabled", example = "true")
        @JsonProperty("companyIn")
        private boolean companyIn;

        @Schema(description = "Indicates whether contract are enabled", example = "true")
        @JsonProperty("contract")
        private boolean contract;

        @Schema(description = "Indicates whether contractIn are enabled", example = "true")
        @JsonProperty("contractIn")
        private boolean contractIn;

        @Schema(description = "Indicates whether processingPlan are enabled", example = "true")
        @JsonProperty("processingPlan")
        private boolean processingPlan;

        @Schema(description = "Indicates whether bundle are enabled", example = "true")
        @JsonProperty("bundle")
        private boolean bundle;

        @Schema(description = "Indicates whether manually are enabled", example = "true")
        @JsonProperty("manually")
        private boolean manually;

        @Schema(description = "Indicates whether productionTask are enabled", example = "true")
        @JsonProperty("productionTask")
        private boolean productionTask;

        @Schema(description = "Indicates whether retailDemand are enabled", example = "true")
        @JsonProperty("retailDemand")
        private boolean retailDemand;

        @Schema(description = "Indicates whether retailSalesReturn are enabled", example = "true")
        @JsonProperty("retailSalesReturn")
        private boolean retailSalesReturn;

        @Schema(description = "Indicates whether factureOut are enabled", example = "true")
        @JsonProperty("factureOut")
        private boolean factureOut;

        @Schema(description = "Indicates whether abstractInventory are enabled", example = "true")
        @JsonProperty("abstractInventory")
        private boolean abstractInventory;

        @Schema(description = "The date and time when the settings were last updated", example = "2024-07-20T10:15:30+05:00")
        @JsonProperty("updateDate")
        private LocalDateTime updateDate;
    }
}
