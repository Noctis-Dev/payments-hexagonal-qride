package com.qride.payments.infraestructure.repository;

import jakarta.persistence.EntityNotFoundException;
import com.qride.payments.domain.models.PaymentProfile;
import com.qride.payments.domain.repository.IPaymentProfileRepository;
import com.qride.payments.infraestructure.mapper.IPaymentProfileMapper;
import com.qride.payments.infraestructure.repository.jpa.PaymentProfileEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentProfileRepositoryImpl implements IPaymentProfileRepository {

    @Autowired
    private IPaymentProfileMapper mapper;

    @Autowired
    private PaymentProfileEntityRepository jpaRepository;

    @Override
    public PaymentProfile save(PaymentProfile paymentProfile) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(paymentProfile)));
    }

    @Override
    public PaymentProfile findByUuid(UUID uuid) {
        return jpaRepository.findByPaymentProfileUuid(uuid)
            .map(mapper::toDomain)
            .orElseThrow(EntityNotFoundException::new);
    }
}
