package com.siriuslab.onec.widget.domain.activation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.siriuslab.onec.widget.app.entity.Auditable;
import com.siriuslab.onec.widget.domain.account.deactivation.entity.DeactivationHistoryEntity;
import com.siriuslab.onec.widget.domain.account.token.entity.AccountEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "application_activation")
@ToString
public class ActivationEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String appUid;
    private UUID accountId;
    private String accountName;
    @Enumerated(EnumType.STRING)
    private Cause cause;
    private UUID tariffId;
    private Boolean trial;
    private String tariffName;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime expiryMoment;
    private Boolean notForResale;
    private Boolean partner;

    @Getter
    public enum Cause {
        INSTALL("Install"),
        RESUME("Resume"),
        TARIFF_CHANGED("TariffChanged"),
        AUTOPROLONGATION("Autoprolongation");

        private final String value;

        Cause(String value) {
            this.value = value;
        }
    }

    @JsonIgnore
    @OneToMany(mappedBy = "activation", orphanRemoval = true)
    private List<AccountEntity> accounts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "activation", orphanRemoval = true)
    private List<DeactivationHistoryEntity> deactivationHistories = new ArrayList<>();
}
