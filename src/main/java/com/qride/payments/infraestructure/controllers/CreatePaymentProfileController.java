package com.qride.payments.infraestructure.controllers;

import com.qride.payments.application.IPaymentProfileService;
import com.qride.payments.application.dto.BaseResponse;
import com.qride.payments.application.dto.request.PaymentProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class CreatePaymentProfileController {

    @Autowired
    private IPaymentProfileService service;

    @PostMapping
    public ResponseEntity<BaseResponse> createPaymentProfile(@RequestBody PaymentProfileRequest request) {
        return service.createPaymentProfile(request).apply();
    }

}
