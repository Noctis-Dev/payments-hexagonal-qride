package com.qride.payments.infraestructure.controllers;

import com.qride.payments.application.IPaymentProfileService;
import com.qride.payments.application.dto.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("oauth")
public class OauthRedirectionController {

    @Autowired
    private IPaymentProfileService service;

    @GetMapping
    public ResponseEntity<BaseResponse> getOAuth(@RequestParam String code, @RequestParam UUID state) {
        try {
            return service.completePaymentProfile(state, code).apply();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
