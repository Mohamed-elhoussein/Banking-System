package employee_api.banking.employee.dto;

public record EmployeeLoginResponse(
        String token,
        String tokenType,
        String email,
        String role
) {}