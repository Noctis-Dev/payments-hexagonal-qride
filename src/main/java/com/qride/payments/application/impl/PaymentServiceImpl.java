package com.qride.payments.application.impl;

import com.qride.payments.application.IPaymentProfileService;
import com.qride.payments.application.IPaymentService;
import com.qride.payments.application.dto.BaseResponse;
import com.qride.payments.application.dto.request.PaymentRequest;
import com.qride.payments.application.dto.response.PaymentResponse;
import com.qride.payments.application.factory.EventFactory;
import com.qride.payments.domain.broker.IMessageProducer;
import com.qride.payments.domain.external.IPayments;
import com.qride.payments.domain.models.Payment;
import com.qride.payments.domain.models.PaymentProfile;
import com.qride.payments.domain.repository.IPaymentRepository;
import com.qride.payments.utils.ThreadsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private IPayments payments;

    @Autowired
    private IPaymentProfileService paymentProfileService;

    @Autowired
    private IPaymentRepository repository;

    @Autowired
    private IMessageProducer producer;

    @Override
    public BaseResponse getPayment(String email) {
        List<PaymentResponse> payments = repository.findByEmail(email).stream().map(payment -> {
            return new PaymentResponse(
                "url",
                LocalDate.now(),
                payment.getPayerName(),
                payment.getPayerEmail(),
                payment.getPayerPhone()
            );
        }).toList();

        return BaseResponse.builder()
            .data(payments)
            .message("Payments retrieved successfully")
            .success(Boolean.TRUE)
            .httpStatus(HttpStatus.OK)
            .status(200).build();
    }

    @Override
    public BaseResponse generatePayment(PaymentRequest request) {
        PaymentProfile profile = paymentProfileService.findByUuid(request.paymentProfileId());

        Payment payment = new Payment();
        payment.setPaymentUuid(UUID.randomUUID());
        payment.setAmount(request.amount());
        payment.setPaymentProfile(profile);
        payment.setPayerName(request.payerName());
        payment.setPayerEmail(request.payerEmail());
        payment.setPayerPhone(request.payerPhoneNumber());

        repository.save(payment);

        String url = payments.generateDonationPaymentUrl("another", payment.getAmount());
        PaymentResponse response = new PaymentResponse(
            url,
            LocalDate.now(),
            payment.getPayerName(),
            payment.getPayerEmail(),
            payment.getPayerPhone()
        );

        ThreadsUtil.runTask(() -> {
            EventFactory factory = EventFactory.builder()
                    .type(request.notifyChannel())
                    .email(request.payerEmail())
                    .phoneNumber(request.payerPhoneNumber())
                    .subject("SCI-ALL Payments")
                    .message("Your payment order has been created on: " + url)
                    .producer(producer).build();

            factory.getNotification().send();
        });

        return BaseResponse.builder()
            .data(response)
            .message("Payment generated successfully")
            .success(Boolean.TRUE)
            .httpStatus(HttpStatus.OK)
            .status(200).build();
    }
}
