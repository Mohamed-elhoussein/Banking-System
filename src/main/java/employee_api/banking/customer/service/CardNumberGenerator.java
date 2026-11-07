
package employee_api.banking.customer.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CardNumberGenerator {

    private final SecureRandom random = new SecureRandom();

    public String generate() {

        StringBuilder cardNumber = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }

        return cardNumber.toString();
    }
}
