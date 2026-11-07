package employee_api.banking.employee.dto;

import employee_api.banking.employee.entity.EmployeeRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateEmployeeRequest(

        @NotBlank
        @Size(max = 150)
        @Email
        String email,

        @NotBlank
        @Size(min = 8, max = 100)
        String password,

        @NotBlank
        @Size(max = 100)
        String firstName,

        @NotBlank
        @Size(max = 100)
        String lastName,

        @Size(max = 100)
        String department,

        @Size(max = 100)
        String jobTitle,

        @NotNull
        EmployeeRole role

) {}