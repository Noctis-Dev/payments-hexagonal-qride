package com.qride.payments.application;

import com.qride.payments.application.dto.BaseResponse;
import com.qride.payments.application.dto.request.PaymentRequest;

public interface IPaymentService {
    BaseResponse getPayment(String email);
    BaseResponse generatePayment(PaymentRequest payment);
}
