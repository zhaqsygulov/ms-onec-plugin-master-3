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

    //Заказы/Счет на оплату
    private boolean customerOrder;

    //Отгрузки/Реализация ТМЗ и Услуг
    private boolean demand;
    private boolean demandIn;

    //Возвраты покупателей/Возврат ТМЗ от покупателя
    private boolean salesReturn;
    private boolean salesReturnIn;

    //Приемки/Поступление ТМЗ и услуг
    private boolean supply;
    private boolean supplyIn;

    //Возвраты поставщикам/Возврат ТМЗ поставщику
    private boolean purchaseReturn;

    //Оприходования/Оприходование ТМЗ
    private boolean enter;
    private boolean enterIn;

    // Входящие
    private boolean paymentIn;
    private boolean paymentInIn;

    //Исходящие
    private boolean paymentOut;
    private boolean paymentOutIn;

    //Приходный кассовый ордер
    private boolean cashIn;
    private boolean cashInIn;

    //Расходный кассовый ордер
    private boolean cashOut;
    private boolean cashOutIn;

    //Заказы поставщику/Заказ поставщику
    private boolean purchaseOrder;

    //Списания/Списание ТМЗ
    private boolean loss;

    //Перемещения/Перемещение ТМЗ
    private boolean move;

    //Тех. операции/Производственные документы (Требование накладная, Отчет о производстве за смену)
    private boolean processing;

    /** reference */

    //Товары/Номенклатура
    private boolean good;
    private boolean goodIn;

    //Контрагенты/Контрагенты
    private boolean company;
    private boolean companyIn;

    //Договоры/Договоры контрагентов
    private boolean contract;
    private boolean contractIn;

    //Тех. карты/Спецификации номенклатур
    private boolean processingPlan;

    //Комплекты/Комплектация ТМЗ или Отчет производства за смену
    private boolean bundle;

    private boolean manually;
    private boolean productionTask;
    private boolean retailDemand;
    private boolean retailSalesReturn;
    private boolean factureOut;
    private boolean abstractInventory;

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
