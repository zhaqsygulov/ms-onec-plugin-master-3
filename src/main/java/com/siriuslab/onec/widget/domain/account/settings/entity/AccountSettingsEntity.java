package com.siriuslab.onec.widget.domain.account.settings.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.siriuslab.onec.widget.app.entity.Auditable;
import com.siriuslab.onec.widget.domain.account.token.entity.AccountEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "account_settings")
@ToString
public class AccountSettingsEntity extends Auditable {

    @Id
    private Long id;

    @Column(name = "company_name")
private String companyName;

@Column(name = "company_description", columnDefinition = "TEXT")
private String companyDescription;

@Column(name = "company_address")
private String companyAddress;

@Column(name = "min_order_sum")
private Double minOrderSum;

@Column(name = "whatsapp_url")
private String whatsappUrl;

@Column(name = "telegram_url")
private String telegramUrl;

@Column(name = "gis2_url")
private String gis2Url;

@Column(name = "logo_path")
private String logoPath;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JsonIgnore
    @ToString.Exclude
    private AccountEntity account;
}
