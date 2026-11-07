package employee_api.banking.employee.config;

import employee_api.banking.employee.entity.BankEmployee;
import employee_api.banking.employee.entity.EmployeeRole;
import employee_api.banking.employee.repository.BankEmployeeRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevelopmentEmployeeSeeder implements CommandLineRunner {

    private final BankEmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.employee.email}")
    private String email;

    @Value("${app.bootstrap.employee.password}")
    private String password;

    public DevelopmentEmployeeSeeder(
            BankEmployeeRepository employeeRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (employeeRepository.existsByEmail(email)) {
            return;
        }

        BankEmployee employee = new BankEmployee();

        employee.setEmail(email);
        employee.setPassword(passwordEncoder.encode(password));
        employee.setFirstName("Test");
        employee.setLastName("Employee");
        employee.setDepartment("Operations");
        employee.setJobTitle("Bank Teller");
        employee.setRole(EmployeeRole.BANK_EMPLOYEE);

        employeeRepository.save(employee);
    }
}