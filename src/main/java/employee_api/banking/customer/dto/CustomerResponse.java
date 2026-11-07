package employee_api.banking.customer.dto;

import java.time.LocalDateTime;
import java.util.UUID;


public record CustomerResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String nationalId,
        String phone,
        String cardNumber,
        String cardLastFour,
        String cardHolderName,
        LocalDateTime createdAt
) {


}