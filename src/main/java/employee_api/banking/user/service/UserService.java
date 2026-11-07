package employee_api.banking.user.service;

import employee_api.banking.customer.entity.Customer;
import employee_api.banking.user.entity.User;
import employee_api.banking.user.entity.UserRole;
import employee_api.banking.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User createCustomerUser(Customer customer, String passwordHash) {

        User user = new User();

        user.setEmail(customer.getEmail());
        user.setPassword(passwordHash);
        user.setRole(UserRole.CUSTOMER);

        userRepository.save(user);

        customer.setUser(user);

        return user;
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
