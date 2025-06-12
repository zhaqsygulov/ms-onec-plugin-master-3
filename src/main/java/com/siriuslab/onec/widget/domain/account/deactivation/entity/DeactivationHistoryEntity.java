package com.siriuslab.onec.widget.domain.account.deactivation.entity;

import com.siriuslab.onec.widget.app.entity.Auditable;
import com.siriuslab.onec.widget.domain.activation.entity.ActivationEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "deactivation_history")
public class DeactivationHistoryEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private UUID accountId;

    @Column(nullable = false)
    private String oldAccessToken;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    private String cause;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activation_id", referencedColumnName = "id")
    private ActivationEntity activation;
}
