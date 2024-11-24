package com.qride.payments.infraestructure.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long id;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "payment_uuid", nullable = false, length = 36)
    private UUID paymentUuid;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Size(max = 100)
    @Column(name = "payer_name", length = 100)
    private String payerName;

    @Size(max = 100)
    @Column(name = "payer_email", length = 100)
    private String payerEmail;

    @Size(max = 20)
    @Column(name = "payer_phone", length = 20)
    private String payerPhone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_profile_id")
    private PaymentProfileEntity paymentProfileEntity;

}