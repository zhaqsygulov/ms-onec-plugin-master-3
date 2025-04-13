package com.siriuslab.onec.widget.domain.activation.dto.activate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppActivationResponse {
    @Builder.Default
    private String status = "Activated";
}
