
package employee_api.banking.customer.service;

import employee_api.banking.customer.dto.CreateCustomerRequest;
import employee_api.banking.customer.dto.CustomerResponse;
import employee_api.banking.customer.entity.Customer;
import employee_api.banking.customer.repository.CustomerRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.DataIntegrityViolationException;
import org.hibernate.exception.ConstraintViolationException;
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CardNumberHasher cardNumberHasher;
    private final CardNumberGenerator cardNumberGenerator;
    private final CustomerSaveService customerSaveService;
    public CustomerService(
            CustomerRepository customerRepository,
            CardNumberHasher cardNumberHasher,
            CardNumberGenerator cardNumberGenerator,
            CustomerSaveService customerSaveService
          
          ) {
        this.customerRepository = customerRepository;
        this.cardNumberHasher = cardNumberHasher;
        this.cardNumberGenerator = cardNumberGenerator;
        this.customerSaveService = customerSaveService;

    }

    public CustomerResponse  createCustomer(CreateCustomerRequest request) {

        if (customerRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email already exists");
        }

        if (customerRepository.existsByNationalId(request.nationalId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "National ID already exists");
        }

        Customer customer = new Customer();

        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setNationalId(request.nationalId());
        customer.setPhone(request.phone());
        
            int maxAttempts = 10;

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {

                String cardNumber = cardNumberGenerator.generate();

                String cardHash = cardNumberHasher.hash(cardNumber);

                if (customerRepository.existsByCardNumberHash(cardHash)) {
                    continue;
                }

                customer.setCardNumberHash(cardHash);

                customer.setCardLastFour(
                        cardNumber.substring(cardNumber.length() - 4)
                );

                customer.setCardHolderName(
                        customer.getFirstName() + " " + customer.getLastName()
                );

                try {

                  Customer  savedCustomer =  customerSaveService.save(customer);
                    return new CustomerResponse(
                              savedCustomer.getId(),
                              savedCustomer.getFirstName(),
                              savedCustomer.getLastName(),
                              savedCustomer.getEmail(),
                              savedCustomer.getNationalId(),
                              savedCustomer.getPhone(),
                              cardNumber,
                              savedCustomer.getCardLastFour(),
                              savedCustomer.getCardHolderName(),
                              savedCustomer.getCreatedAt()
                      );

                } catch (DataIntegrityViolationException exception) {

                    if (!isCardHashDuplicate(exception)) {
                        throw exception;
                    }
                }
            }

            throw new IllegalStateException(
                    "Unable to generate a unique card number"
            );
    }


    

    private boolean isCardHashDuplicate(DataIntegrityViolationException exception) {

    Throwable cause = exception;

    while (cause != null) {

        if (cause instanceof ConstraintViolationException violation) {

            return "uk_customer_card_number_hash"
                    .equals(violation.getConstraintName());
        }

        cause = cause.getCause();
    }

    return false;
}

}
