package employee_api.banking.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerRegistrationRequest(

        @NotBlank
        @Email
        @Size(max = 150)
        String email,

        @NotBlank
        @Size(max = 20)
        String nationalId,

        @NotBlank
        @Size(min = 16, max = 19)
        String cardNumber,

        @NotBlank
        @Size(min = 8, max = 100)
        String password

) {}