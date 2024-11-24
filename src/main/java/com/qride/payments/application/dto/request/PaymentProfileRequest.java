package com.qride.payments.application.dto.request;

public record PaymentProfileRequest(
    String profileName,
    String accountEmail,
    String phoneNumber
) { }
