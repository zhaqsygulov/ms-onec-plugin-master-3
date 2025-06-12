package com.siriuslab.onec.widget.domain.tarif.entity;

import com.siriuslab.onec.widget.app.entity.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
/*@Entity
@Table(name = "tariff")*/
public class TariffEntity  extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private UUID tariffId;

    private String tariffName;
}
