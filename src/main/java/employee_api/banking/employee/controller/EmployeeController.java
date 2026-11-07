package employee_api.banking.employee.controller;

import employee_api.banking.employee.dto.EmployeeLoginRequest;
import employee_api.banking.employee.dto.EmployeeLoginResponse;
import employee_api.banking.employee.service.BankEmployeeService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final BankEmployeeService employeeService;

    public EmployeeController(BankEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/login")
    public EmployeeLoginResponse login(
            @Valid @RequestBody EmployeeLoginRequest request
    ) {
        return employeeService.login(request);
    }
}