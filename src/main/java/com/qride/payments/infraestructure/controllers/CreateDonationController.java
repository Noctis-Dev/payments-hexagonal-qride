package com.qride.payments.infraestructure.controllers;

import com.qride.payments.application.IPaymentService;
import com.qride.payments.application.dto.BaseResponse;
import com.qride.payments.application.dto.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/donations")
public class CreateDonationController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping
    public ResponseEntity<BaseResponse> createDonation(@RequestBody PaymentRequest request) {
        return paymentService.generatePayment(request).apply();
    }

}
