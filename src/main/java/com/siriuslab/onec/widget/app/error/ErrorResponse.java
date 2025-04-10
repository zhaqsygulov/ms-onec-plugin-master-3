package com.siriuslab.onec.widget.app.error;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String details;
}
