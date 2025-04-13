package com.siriuslab.onec.widget.domain.activation.api;

import com.siriuslab.onec.widget.domain.ms.service.MsFieldCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ms/custom-fields")
@RequiredArgsConstructor
public class CustomFieldsController {

    private final MsFieldCreationService msFieldService;

    @PostMapping("/product-fields")
    public ResponseEntity<Void> createProductFields(@RequestHeader("Authorization") String auth) {
        msFieldService.createProductFields(auth.replace("Bearer ", "").trim());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/order-fields")
    public ResponseEntity<Void> createOrderFields(@RequestHeader("Authorization") String auth) {
        msFieldService.createOrderFields(auth.replace("Bearer ", "").trim());
        return ResponseEntity.ok().build();
    }
}
