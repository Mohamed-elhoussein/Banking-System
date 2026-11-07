package employee_api.banking.employee.service;

import employee_api.banking.employee.dto.CreateEmployeeRequest;
import employee_api.banking.employee.dto.EmployeeResponse;
import employee_api.banking.employee.entity.BankEmployee;
import employee_api.banking.employee.mapper.EmployeeMapper;
import employee_api.banking.employee.repository.BankEmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import employee_api.banking.employee.dto.EmployeeLoginRequest;
import employee_api.banking.employee.dto.EmployeeLoginResponse;
import employee_api.security.JwtService;
@Service
public class BankEmployeeService {

    private final BankEmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeMapper employeeMapper;
    private final JwtService jwtService;
    public BankEmployeeService(
            BankEmployeeRepository employeeRepository,
            PasswordEncoder passwordEncoder,
            EmployeeMapper employeeMapper,
            JwtService jwtService
    ) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeMapper = employeeMapper;
        this.jwtService = jwtService;
    }

    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {

        if (employeeRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        BankEmployee employee = new BankEmployee();

        employee.setEmail(request.email());
        employee.setPassword(passwordEncoder.encode(request.password()));
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setDepartment(request.department());
        employee.setJobTitle(request.jobTitle());
        employee.setRole(request.role());

        BankEmployee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.toResponse(savedEmployee);
    }

        public EmployeeLoginResponse login(EmployeeLoginRequest request) {

        BankEmployee employee = employeeRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email or password"
                ));

        if (!employee.isEnabled()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Employee account is disabled"
            );
        }

        if (!passwordEncoder.matches(
                request.password(),
                employee.getPassword()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password"
            );
        }

        String token = jwtService.generateToken(
                employee.getEmail(),
                employee.getRole().name()
        );

        return new EmployeeLoginResponse(
                token,
                "Bearer",
                employee.getEmail(),
                employee.getRole().name()
        );
    }
}