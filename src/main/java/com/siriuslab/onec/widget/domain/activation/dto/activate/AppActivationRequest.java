package com.siriuslab.onec.widget.domain.activation.dto.activate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class AppActivationRequest {

    private String appUid;
    private UUID accountId;
    private String accountName;
    private Cause cause;
    private List<Access> access;
    private Subscription subscription;
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Jacksonized
    public static class Access {
        private String resource;
        private List<String> scope;
        @JsonProperty("access_token")
        private String accessToken;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Jacksonized
    public static class Subscription {
        private String tariffId;
        private boolean trial;
        private String tariffName;
        private OffsetDateTime expiryMoment;
        private boolean notForResale;
        private boolean partner;
    }

    public enum Cause {
        INSTALL("Install"),
        RESUME("Resume"),
        TARIFF_CHANGED("TariffChanged"),
        AUTOPROLONGATION("Autoprolongation");

        private final String value;

        Cause(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @JsonCreator
        public static Cause fromValue(String value) {
            for (Cause cause : Cause.values()) {
                if (cause.getValue().equalsIgnoreCase(value)) {
                    return cause;
                }
            }
            throw new IllegalArgumentException("Unknown enum value: " + value);
        }
    }
}
