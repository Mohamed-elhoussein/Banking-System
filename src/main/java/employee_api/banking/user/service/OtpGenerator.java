package employee_api.banking.user.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OtpGenerator {

    private final SecureRandom secureRandom = new SecureRandom();

    public String generate() {
        int otp = secureRandom.nextInt(1_000_000);
        return String.format("%06d", otp);
    }
}