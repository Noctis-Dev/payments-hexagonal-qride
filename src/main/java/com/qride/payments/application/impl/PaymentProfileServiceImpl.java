package com.qride.payments.application.impl;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.qride.payments.application.IPaymentProfileService;
import com.qride.payments.application.dto.BaseResponse;
import com.qride.payments.application.dto.request.PaymentProfileRequest;
import com.qride.payments.application.dto.response.PaymentProfileResponse;
import com.qride.payments.application.factory.EventFactory;
import com.qride.payments.domain.broker.IMessageProducer;
import com.qride.payments.domain.events.EventType;
import com.qride.payments.domain.external.IOauth;
import com.qride.payments.domain.models.PaymentProfile;
import com.qride.payments.domain.repository.IPaymentProfileRepository;
import com.qride.payments.utils.ThreadsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentProfileServiceImpl implements IPaymentProfileService {

    @Autowired
    private IPaymentProfileRepository repository;

    @Autowired
    private IOauth oauth;

    @Autowired
    private IMessageProducer producer;

    @Override
    public BaseResponse createPaymentProfile(PaymentProfileRequest request) {
        UUID uuid = UUID.randomUUID();
        String oauthUrl = oauth.generateOauthUrl(uuid);

        PaymentProfile paymentProfile = new PaymentProfile();
        paymentProfile.setPaymentProfileUuid(uuid);
        paymentProfile.setProfileName(request.profileName());
        paymentProfile.setAccountEmail(request.accountEmail());
        paymentProfile.setPhoneNumber(request.phoneNumber());
        paymentProfile.setOauthUrl(oauthUrl);

        repository.save(paymentProfile);

        PaymentProfileResponse response = new PaymentProfileResponse(
            paymentProfile.getPaymentProfileUuid(),
            paymentProfile.getProfileName(),
            paymentProfile.getAccountEmail(),
            paymentProfile.getOauthUrl()
        );

        return BaseResponse.builder()
            .data(response)
            .message("Payment profile created successfully")
            .success(Boolean.TRUE)
            .httpStatus(HttpStatus.OK)
            .status(200).build();
    }

    @Override
    public BaseResponse completePaymentProfile(UUID uuid, String code) throws MPException, MPApiException {
        PaymentProfile paymentProfile = repository.findByUuid(uuid);
        String accessToken = oauth.exchangeCodeForToken(code);

        paymentProfile.setOauthToken(accessToken);
        repository.save(paymentProfile);

        ThreadsUtil.runTask(() -> {
            EventFactory factory = EventFactory.builder()
                .type(EventType.EMAIL)
                .subject("Payment profile completed")
                .message("Your payment profile was completed successfully")
                .email(paymentProfile.getAccountEmail())
                .producer(producer).build();

            factory.getNotification().send();
        });

        return BaseResponse.builder()
            .data(null)
            .message("Payment profile completed successfully")
            .success(Boolean.TRUE)
            .httpStatus(HttpStatus.OK)
            .status(200).build();
    }

    @Override
    public PaymentProfile findByUuid(UUID uuid) {
        return repository.findByUuid(uuid);
    }
}
