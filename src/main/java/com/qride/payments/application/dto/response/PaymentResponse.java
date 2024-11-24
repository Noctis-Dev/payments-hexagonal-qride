package com.qride.payments.application.dto.response;

import java.time.LocalDate;

public record PaymentResponse(
    String paymentUrl,
    LocalDate createdAt,
    String payerName,
    String payerEmail,
    String payerPhoneNumber
) { }
