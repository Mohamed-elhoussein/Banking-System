package employee_api.banking.user.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevelopmentOtpSender implements OtpSender {

    @Override
    public void send(String phone, String otp) {
        System.out.println("OTP for " + phone + ": " + otp);
    }
}