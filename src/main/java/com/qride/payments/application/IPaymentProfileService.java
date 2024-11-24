package com.qride.payments.application;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.qride.payments.application.dto.BaseResponse;
import com.qride.payments.application.dto.request.PaymentProfileRequest;
import com.qride.payments.domain.models.PaymentProfile;

import java.util.UUID;

public interface IPaymentProfileService {
    BaseResponse createPaymentProfile(PaymentProfileRequest request);
    BaseResponse completePaymentProfile(UUID uuid, String code) throws MPException, MPApiException;
    PaymentProfile findByUuid(UUID uuid);
}
