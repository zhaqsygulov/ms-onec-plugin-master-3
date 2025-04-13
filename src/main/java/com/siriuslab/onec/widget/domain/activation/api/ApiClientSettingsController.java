package com.siriuslab.onec.widget.domain.activation.api;

import com.siriuslab.onec.widget.domain.account.settings.service.AccountSettingsService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class ApiClientSettingsController {

    private final AccountSettingsService settingsService;

    @PutMapping("/{accountId}/settings")
    public ResponseEntity<Void> updateSettings(
            @PathVariable UUID accountId,
            @RequestParam String companyName,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String address,
            @RequestParam double minOrderSum,
            @RequestParam String whatsapp,
            @RequestParam(required = false) String telegram,
            @RequestParam(required = false) String gis2,
            @RequestPart(required = false) MultipartFile logo
    ) {
        settingsService.saveClientSettings(accountId, companyName, description, address, minOrderSum, whatsapp, telegram, gis2, logo);
        return ResponseEntity.ok().build();
    }
}
