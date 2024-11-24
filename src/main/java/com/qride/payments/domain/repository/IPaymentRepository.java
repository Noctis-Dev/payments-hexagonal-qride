package com.qride.payments.domain.repository;

import com.qride.payments.domain.models.Payment;

import java.util.List;

public interface IPaymentRepository {
    Payment save(Payment payment);
    List<Payment> findByEmail(String email);
}
