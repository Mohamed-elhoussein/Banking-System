package employee_api.banking.customer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

@Component
public class CardNumberHasher {

    private final String secret;

    public CardNumberHasher(
            @Value("${app.security.card-hash-secret}") String secret) {
        this.secret = secret;
    }

    public String hash(String cardNumber) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");

            SecretKeySpec key = new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            );

            mac.init(key);

            byte[] hash = mac.doFinal(
                    cardNumber.getBytes(StandardCharsets.UTF_8)
            );

            return HexFormat.of().formatHex(hash);

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Unable to hash card number", exception
            );
        }
    }
}