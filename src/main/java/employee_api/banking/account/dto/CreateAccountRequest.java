package employee_api.banking.account.dto;

import employee_api.banking.account.entity.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record CreateAccountRequest(

        @NotNull(message = "Customer ID is required")
        UUID customerId,

        @NotNull(message = "Account type is required")
        AccountType accountType,

        @NotBlank(message = "Currency is required")
        @Pattern(regexp = "^[A-Z]{3}$",
                 message = "Currency must be a 3-letter uppercase code")
        String currency

) {}