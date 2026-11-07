package employee_api.banking.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmployeeLoginRequest(

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password

) {}