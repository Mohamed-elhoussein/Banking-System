package employee_api.banking.user.service;

public interface OtpSender {

    void send(String phone, String otp);

}