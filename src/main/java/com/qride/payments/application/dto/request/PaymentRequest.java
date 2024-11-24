package com.qride.payments.application.dto.request;

import com.qride.payments.domain.events.EventType;

import java.util.UUID;

public record PaymentRequest(
    EventType notifyChannel,
    String payerName,
    String payerEmail,
    String payerPhoneNumber,
    UUID paymentProfileId,
    Double amount
) { }
