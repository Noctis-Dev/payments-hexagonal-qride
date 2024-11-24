package com.qride.payments.infraestructure.external;

import com.mercadopago.client.oauth.CreateOauthCredentialRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.oauth.CreateOauthCredential;
import com.mercadopago.serialization.Serializer;
import com.qride.payments.domain.external.IOauth;
import com.qride.payments.infraestructure.configuration.MercadoPagoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class MercadoPagoOauthImpl implements IOauth {

    @Autowired
    private MercadoPagoConfig config;

    @Override
    public String generateOauthUrl(UUID uuid) {
        String BASE_URL = "https://auth.mercadopago.com.mx/authorization";

        BASE_URL += "?client_id=" + config.getClientId();
        BASE_URL += "&response_type=code";
        BASE_URL += "&platform_id=mp";
        BASE_URL += "&state=" + uuid;
        BASE_URL += "&redirect_uri=" + config.getRedirectUri();

        return BASE_URL;
    }

    @Override
    public String exchangeCodeForToken(String code) throws MPException, MPApiException {
        CreateOauthCredentialRequest request = CreateOauthCredentialRequest.builder()
                .code(code)
                .clientId(config.getClientId())
                .clientSecret(config.getClientSecret())
                .redirectUri(config.getRedirectUri()).build();


        MPHttpClient client = com.mercadopago.MercadoPagoConfig.getHttpClient();
        MPRequest mpRequest = getRequest(request);
        MPResponse response = client.send(mpRequest);

        return Serializer.deserializeFromJson(CreateOauthCredential.class, response.getContent()).getAccessToken();
    }

    private MPRequest getRequest(CreateOauthCredentialRequest request) {
        return MPRequest.builder()
                .uri("https://api.mercadopago.com/oauth/token")
                .method(HttpMethod.POST)
                .accessToken(config.getAccessToken())
                .headers(Map.of("Content-Type", "application/json"))
                .payload(Serializer.serializeToJson(request)).build();
    }
}
