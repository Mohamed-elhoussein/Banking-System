package employee_api.banking.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VerifyRegistrationOtpRequest(

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 6, max = 6)
        String otp

) {}