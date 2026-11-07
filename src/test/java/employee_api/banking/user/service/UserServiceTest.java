
package employee_api.banking.user.service;

import employee_api.banking.customer.entity.Customer;
import employee_api.banking.user.entity.User;
import employee_api.banking.user.entity.UserRole;
import employee_api.banking.user.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createCustomerUser_shouldCreateAndLinkCustomerUser() {

        // Arrange
        Customer customer = new Customer();
        customer.setEmail("customer@bank.com");

        String passwordHash = "hashed-password";

        // Act
        User result = userService.createCustomerUser(
                customer,
                passwordHash
        );

        // Assert
        assertNotNull(result);
        assertEquals("customer@bank.com", result.getEmail());
        assertEquals(passwordHash, result.getPassword());
        assertEquals(UserRole.CUSTOMER, result.getRole());

        assertSame(result, customer.getUser());

        verify(userRepository, times(1)).save(result);
    }

    @Test
    void encodePassword_shouldReturnEncodedPassword() {

        // Arrange
        String rawPassword = "CustomerPass123!";
        String encodedPassword = "hashed-password";

        when(passwordEncoder.encode(rawPassword))
                .thenReturn(encodedPassword);

        // Act
        String result = userService.encodePassword(rawPassword);

        // Assert
        assertEquals(encodedPassword, result);

        verify(passwordEncoder, times(1)).encode(rawPassword);
    }
}
