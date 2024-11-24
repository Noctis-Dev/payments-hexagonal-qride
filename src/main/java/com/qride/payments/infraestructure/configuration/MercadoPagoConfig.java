package com.qride.payments.infraestructure.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@ConfigurationProperties(prefix = "mp")
public class MercadoPagoConfig {
    private String accessToken;
    private String clientSecret;
    private String clientId;
    private String redirectUri;
}
