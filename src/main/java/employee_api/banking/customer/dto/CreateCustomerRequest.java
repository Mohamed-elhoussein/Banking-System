package employee_api.banking.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record CreateCustomerRequest(

        @NotBlank(message = "First name is required")
        @Size(max = 100)
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100)
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "National ID is required")
        @Size(min = 14, max = 14)
        String nationalId,

        @NotBlank(message = "Phone is required")
        @Size(max = 20)
        String phone



) {}