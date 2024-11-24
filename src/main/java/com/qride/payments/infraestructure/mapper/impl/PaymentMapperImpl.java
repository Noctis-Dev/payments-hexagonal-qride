package com.qride.payments.infraestructure.mapper.impl;

import com.qride.payments.domain.models.Payment;
import com.qride.payments.infraestructure.entities.PaymentEntity;
import com.qride.payments.infraestructure.mapper.IPaymentMapper;
import com.qride.payments.infraestructure.mapper.IPaymentProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapperImpl implements IPaymentMapper {

    @Autowired
    private IPaymentProfileMapper paymentProfileMapper;

    @Override
    public Payment toDomain(PaymentEntity entity) {
        Payment payment = new Payment();

        payment.setId(entity.getId());
        payment.setPaymentUuid(entity.getPaymentUuid());
        payment.setAmount(entity.getAmount());
        payment.setPayerName(entity.getPayerName());
        payment.setPayerEmail(entity.getPayerEmail());
        payment.setPayerPhone(entity.getPayerPhone());
        payment.setPaymentProfile(paymentProfileMapper.toDomain(entity.getPaymentProfileEntity()));

        return payment;
    }

    @Override
    public PaymentEntity toEntity(Payment domain) {
        PaymentEntity entity = new PaymentEntity();

        entity.setId(domain.getId());
        entity.setPaymentUuid(domain.getPaymentUuid());
        entity.setAmount(domain.getAmount());
        entity.setPayerName(domain.getPayerName());
        entity.setPayerEmail(domain.getPayerEmail());
        entity.setPayerPhone(domain.getPayerPhone());
        entity.setPaymentProfileEntity(paymentProfileMapper.toEntity(domain.getPaymentProfile()));

        return entity;
    }
}
