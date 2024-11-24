package com.qride.payments.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class PaymentProfile {
    private Long id;
    private UUID paymentProfileUuid;
    private String profileName;
    private String accountEmail;
    private String phoneNumber;
    private String oauthUrl;
    private String oauthToken;
}
