package com.qride.payments.infraestructure.mapper.impl;

import com.qride.payments.domain.models.PaymentProfile;
import com.qride.payments.infraestructure.entities.PaymentProfileEntity;
import com.qride.payments.infraestructure.mapper.IPaymentProfileMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentProfileMapperImpl implements IPaymentProfileMapper {
    @Override
    public PaymentProfile toDomain(PaymentProfileEntity entity) {
        PaymentProfile paymentProfile = new PaymentProfile();

        paymentProfile.setId(entity.getId());
        paymentProfile.setPaymentProfileUuid(entity.getPaymentProfileUuid());
        paymentProfile.setProfileName(entity.getProfileName());
        paymentProfile.setAccountEmail(entity.getAccountEmail());
        paymentProfile.setPhoneNumber(entity.getPhoneNumber());
        paymentProfile.setOauthUrl(entity.getOauthUrl());
        paymentProfile.setOauthToken(entity.getOauthToken());

        return paymentProfile;
    }

    @Override
    public PaymentProfileEntity toEntity(PaymentProfile domain) {
        PaymentProfileEntity entity = new PaymentProfileEntity();

        entity.setId(domain.getId());
        entity.setPaymentProfileUuid(domain.getPaymentProfileUuid());
        entity.setProfileName(domain.getProfileName());
        entity.setAccountEmail(domain.getAccountEmail());
        entity.setPhoneNumber(domain.getPhoneNumber());
        entity.setOauthUrl(domain.getOauthUrl());
        entity.setOauthToken(domain.getOauthToken());

        return entity;
    }
}
