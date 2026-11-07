package employee_api.banking.user.service;

import employee_api.banking.customer.entity.Customer;
import employee_api.banking.user.entity.User;
import employee_api.banking.customer.repository.CustomerRepository;
import employee_api.banking.customer.service.CardNumberHasher;
import employee_api.banking.user.dto.CustomerRegistrationRequest;
import employee_api.banking.user.dto.VerifyRegistrationOtpRequest;
import employee_api.banking.user.repository.UserRepository;
import employee_api.banking.user.service.RegistrationCacheService;
import employee_api.banking.user.service.OtpGenerator;
import employee_api.banking.user.service.OtpSender;
import employee_api.banking.user.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
@Service
public class CustomerRegistrationService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final CardNumberHasher cardNumberHasher;
    private final RegistrationCacheService registrationCacheService;
    private final OtpGenerator otpGenerator;
    private final OtpSender otpSender;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    
    public CustomerRegistrationService(
            CustomerRepository customerRepository,
            UserRepository userRepository,
            CardNumberHasher cardNumberHasher,
            RegistrationCacheService registrationCacheService,
            OtpGenerator otpGenerator,
            OtpSender otpSender,
            PasswordEncoder passwordEncoder,
            UserService userService
    ) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.cardNumberHasher = cardNumberHasher;

        this.registrationCacheService = registrationCacheService;
        this.otpGenerator = otpGenerator;
        this.otpSender = otpSender;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public Customer validateCustomer(CustomerRegistrationRequest request) {

        Customer customer = customerRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid registration details"
                ));

        if (!customer.getNationalId().equals(request.nationalId())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid registration details"
            );
        }

        String cardHash = cardNumberHasher.hash(request.cardNumber());

        if (!cardHash.equals(customer.getCardNumberHash())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid registration details"
            );
        }

        if (customer.getUser() != null ||
                userRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Customer is already registered"
            );
        }

        return customer;
    }

@Transactional
public void register(CustomerRegistrationRequest request) {

    // 1. Validate customer information
    Customer customer = validateCustomer(request);

    // 2. Generate OTP
    String otp = otpGenerator.generate();

    // 3. Hash OTP and password
    String otpHash = passwordEncoder.encode(otp);
    String passwordHash = userService.encodePassword(request.password());
    // 4. Save temporary registration data in Redis
    registrationCacheService.save(
            customer.getId(),
            otpHash,
            passwordHash
    );

    // 5. Send OTP to customer's registered phone
    otpSender.send(customer.getPhone(), otp);
}

@Transactional
public User verifyOtp(VerifyRegistrationOtpRequest request) {

    // 1. Find existing customer
    Customer customer = customerRepository.findByEmailForUpdate(request.email())
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid OTP"
            ));

            if (customer.getUser() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Customer is already registered"
            );
        }

    // 2. Get OTP hash from Redis
    String otpHash = registrationCacheService.getOtpHash(customer.getId());

    if (otpHash == null) {
        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "OTP expired or not found"
        );
    }

    // 3. Verify OTP
    if (!passwordEncoder.matches(request.otp(), otpHash)) {

        Long attempts = registrationCacheService.incrementAttempts(customer.getId());

        if (attempts >= 5) {
            registrationCacheService.delete(customer.getId());
        }

        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid OTP"
        );
    }

    // 4. Get temporary password hash
      String passwordHash = registrationCacheService.getPasswordHash(customer.getId());

      if (passwordHash == null) {
          throw new ResponseStatusException(
                  HttpStatus.UNAUTHORIZED,
                  "Registration session expired"
          );
      }

      // 5. Create customer user
      User user = userService.createCustomerUser(customer, passwordHash);

      // 6. Delete temporary registration data
      TransactionSynchronizationManager.registerSynchronization(
          new TransactionSynchronization() {
              @Override
              public void afterCommit() {
                  registrationCacheService.delete(customer.getId());
              }
          }
      );
      return user;
  }


  
}