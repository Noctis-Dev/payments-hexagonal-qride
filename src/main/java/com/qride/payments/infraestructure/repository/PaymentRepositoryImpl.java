package com.qride.payments.infraestructure.repository;

import com.qride.payments.domain.models.Payment;
import com.qride.payments.domain.repository.IPaymentRepository;
import com.qride.payments.infraestructure.mapper.IPaymentMapper;
import com.qride.payments.infraestructure.repository.jpa.PaymentEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentRepositoryImpl implements IPaymentRepository {

    @Autowired
    private PaymentEntityRepository jpaRepository;

    @Autowired
    private IPaymentMapper mapper;

    @Override
    public Payment save(Payment payment) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(payment)));
    }

    @Override
    public List<Payment> findByEmail(String email) {
        return jpaRepository.findByPayerEmail(email).stream().map(mapper::toDomain).toList();
    }
}
