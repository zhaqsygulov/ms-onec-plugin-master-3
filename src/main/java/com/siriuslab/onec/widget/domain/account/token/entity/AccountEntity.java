package com.siriuslab.onec.widget.domain.account.token.entity;

import com.siriuslab.onec.widget.app.entity.Auditable;
import com.siriuslab.onec.widget.domain.account.settings.entity.AccountSettingsEntity;
import com.siriuslab.onec.widget.domain.activation.entity.ActivationEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "account")
public class AccountEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "account_id", nullable = false, unique = true)
    private UUID accountId;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private Boolean activated;

    @Column(nullable = false)
    private Boolean suspended;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activation_id", referencedColumnName = "id")
    private ActivationEntity activation;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private AccountSettingsEntity settings;
}
