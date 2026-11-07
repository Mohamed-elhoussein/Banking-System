package employee_api.banking.user.controller;

import employee_api.banking.user.dto.CustomerRegistrationRequest;
import employee_api.banking.user.dto.CustomerUserResponse;
import employee_api.banking.user.dto.VerifyRegistrationOtpRequest;
import employee_api.banking.user.entity.User;
import employee_api.banking.user.service.CustomerRegistrationService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import employee_api.banking.user.mapper.UserMapper;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CustomerRegistrationService customerRegistrationService;
    private final UserMapper userMapper;

    public UserController(
            CustomerRegistrationService customerRegistrationService,UserMapper userMapper) {
        this.customerRegistrationService = customerRegistrationService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void register(
            @Valid @RequestBody CustomerRegistrationRequest request) {

        customerRegistrationService.register(request);
    }

    @PostMapping("/verify-otp")
    public CustomerUserResponse  verifyOtp(
            @Valid @RequestBody VerifyRegistrationOtpRequest request) {

        User user = customerRegistrationService.verifyOtp(request);

        return   userMapper.toResponse(user);
    }
}