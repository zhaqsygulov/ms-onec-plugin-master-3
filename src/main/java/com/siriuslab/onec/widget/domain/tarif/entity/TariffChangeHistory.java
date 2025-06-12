package com.siriuslab.onec.widget.domain.tarif.entity;

import com.siriuslab.onec.widget.app.entity.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
/*@Entity
@Table(name = "tariff_change_history")*/
public class TariffChangeHistory extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

}
