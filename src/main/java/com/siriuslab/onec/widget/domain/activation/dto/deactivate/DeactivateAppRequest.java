package com.siriuslab.onec.widget.domain.activation.dto.deactivate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class DeactivateAppRequest {
    private String appUid;
    private String accountName;
    private Cause cause;
    @Getter
    public enum Cause {
        UNINSTALL("Uninstall"),
        SUSPEND("Suspend");

        private final String value;

        Cause(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @JsonCreator
        public static DeactivateAppRequest.Cause fromValue(String value) {
            for (DeactivateAppRequest.Cause cause : DeactivateAppRequest.Cause.values()) {
                if (cause.getValue().equalsIgnoreCase(value)) {
                    return cause;
                }
            }
            throw new IllegalArgumentException("Unknown enum value: " + value);
        }
    }
}

