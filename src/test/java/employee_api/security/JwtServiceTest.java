
package employee_api.security;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void generateToken_shouldReturnDifferentTokens() {

        String token1 = jwtService.generateToken(
                "customer@bank.com", "CUSTOMER"
        );

        String token2 = jwtService.generateToken(
                "customer@bank.com", "CUSTOMER"
        );

        assertNotEquals(token1, token2);
    }

    @Test
    void extractEmail_shouldReturnCorrectEmail() {

        String token = jwtService.generateToken(
                "customer@bank.com", "CUSTOMER"
        );

        String email = jwtService.extractEmail(token);

        assertEquals("customer@bank.com", email);
    }

    @Test
    void extractRole_shouldReturnCorrectRole() {

        String token = jwtService.generateToken(
                "employee@bank.com", "BANK_EMPLOYEE"
        );

        String role = jwtService.extractRole(token);

        assertEquals("BANK_EMPLOYEE", role);
    }

    @Test
    void isTokenValid_shouldReturnTrueForCorrectEmail() {

        String token = jwtService.generateToken(
                "customer@bank.com", "CUSTOMER"
        );

        assertTrue(
                jwtService.isTokenValid(token, "customer@bank.com")
        );
    }

    @Test
    void isTokenValid_shouldReturnFalseForDifferentEmail() {

        String token = jwtService.generateToken(
                "customer@bank.com", "CUSTOMER"
        );

        assertFalse(
                jwtService.isTokenValid(token, "other@bank.com")
        );
    }

    @Test
    void extractEmail_shouldThrowExceptionForInvalidToken() {

        assertThrows(
                JwtException.class,
                () -> jwtService.extractEmail("invalid-token")
        );
    }
}
