package employee_api.banking.customer.controller;

import employee_api.banking.customer.dto.CreateCustomerRequest;
import employee_api.banking.customer.dto.CustomerResponse;
import employee_api.banking.customer.entity.Customer;
import employee_api.banking.customer.service.CustomerService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import employee_api.banking.customer.mapper.CustomerMapper;
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(
            @Valid @RequestBody CreateCustomerRequest request
    ) {

        // Customer customer = customerService.createCustomer(request);
      return customerService.createCustomer(request);
        // return customerMapper.toResponse(customer);
    }
}