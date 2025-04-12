package com.siriuslab.onec.widget.domain.activation.dto.status;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppStatusResponse {
    @Builder.Default
    private String status = "Activated";
}
