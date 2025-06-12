package com.siriuslab.onec.widget.domain.activation.dto.activate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppActivationResponse {
    @Builder.Default
    private String status = "Activated";
}
