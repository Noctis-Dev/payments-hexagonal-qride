package com.qride.payments.domain.repository;

import com.qride.payments.domain.models.PaymentProfile;

import java.util.UUID;

public interface IPaymentProfileRepository {
    PaymentProfile save(PaymentProfile paymentProfile);
    PaymentProfile findByUuid(UUID uuid);
}
