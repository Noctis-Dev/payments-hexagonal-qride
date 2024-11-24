package com.qride.payments.domain.external;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

import java.util.UUID;

public interface IOauth {
    String generateOauthUrl(UUID uuid);
    String exchangeCodeForToken(String code) throws MPException, MPApiException;
}
