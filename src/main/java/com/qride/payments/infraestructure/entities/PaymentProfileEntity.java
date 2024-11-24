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
@Table(name = "payment_profiles")
public class PaymentProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_profile_id", nullable = false)
    private Long id;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "payment_profile_uuid", nullable = false, length = 36)
    private UUID paymentProfileUuid;

    @Size(max = 100)
    @NotNull
    @Column(name = "profile_name", nullable = false, length = 100)
    private String profileName;

    @Size(max = 100)
    @NotNull
    @Column(name = "account_email", nullable = false, length = 100)
    private String accountEmail;

    @Size(max = 20)
    @NotNull
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Size(max = 255)
    @NotNull
    @Column(name = "oauth_url", nullable = false, length = 255)
    private String oauthUrl;

    @Size(max = 255)
    @Column(name = "oauth_token", length = 255)
    private String oauthToken;

}