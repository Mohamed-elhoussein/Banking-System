package employee_api.banking.user.dto;

import employee_api.banking.user.entity.UserRole;
import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerUserResponse(
        UUID id,
        String email,
        UserRole role,
        boolean enabled,
        LocalDateTime createdAt
) {}