package com.qride.payments.infraestructure.external;

import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.qride.payments.domain.external.IPayments;
import com.qride.payments.infraestructure.configuration.MercadoPagoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class MercadoPagoPaymentsImpl implements IPayments {

    @Autowired
    private MercadoPagoConfig config;

    @Override
    public String generateDonationPaymentUrl(String to, Double amount) {
        com.mercadopago.MercadoPagoConfig.setAccessToken(config.getAccessToken());

        PreferenceClient client = new PreferenceClient();
        Preference preference = null;

        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title("Donation to: " + to).quantity(1)
                .unitPrice(BigDecimal.valueOf(amount)).build();

        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .email(to).build();

        PreferenceRequest request = PreferenceRequest.builder()
                .items(List.of(item))
                .payer(payer).build();

        try {
            preference = client.create(request);
        } catch (MPApiException | MPException e) {
            throw new RuntimeException(e);
        }

        return preference.getInitPoint();
    }

}
