package employee_api.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.List;

@Configuration
public class SecurityConfig {

  @Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity http,
        JwtAuthenticationFilter jwtAuthenticationFilter
) throws Exception {

    http
        .cors(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/user/login").permitAll()
            .requestMatchers(HttpMethod.POST,"/api/v1/employees/login").permitAll()
            .requestMatchers(
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/v3/api-docs/**"
                        ).permitAll()

            .requestMatchers(
                          HttpMethod.POST,
                          "/api/v1/users/register",
                          "/api/v1/users/verify-otp",
                          "/api/v1/users/login"
                      ).permitAll()
                      
            .requestMatchers(HttpMethod.POST, "/api/v1/customers")
            .hasRole("BANK_EMPLOYEE")

            .anyRequest().authenticated()
        )
        .addFilterBefore(
            jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class
        );

    return http.build();
}

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(
            List.of("http://localhost:3000")
        );

        config.setAllowedMethods(
            List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        );

        config.setAllowedHeaders(
            List.of("Content-Type", "Authorization")
        );

        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}