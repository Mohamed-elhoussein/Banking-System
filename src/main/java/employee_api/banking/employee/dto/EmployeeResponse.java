package employee_api.banking.employee.dto;

import employee_api.banking.employee.entity.EmployeeRole;
import java.time.LocalDateTime;
import java.util.UUID;

public record EmployeeResponse(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String department,
        String jobTitle,
        EmployeeRole role,
        boolean enabled,
        LocalDateTime createdAt
) {}