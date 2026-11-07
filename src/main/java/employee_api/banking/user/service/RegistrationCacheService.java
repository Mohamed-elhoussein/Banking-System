package employee_api.banking.user.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Service
public class RegistrationCacheService {

    private final StringRedisTemplate redisTemplate;

    private static final Duration OTP_TTL = Duration.ofMinutes(5);

    public RegistrationCacheService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(
            UUID customerId,
            String otpHash,
            String passwordHash
    ) {
        String key = "customer:registration:" + customerId;

        Map<String, String> data = Map.of(
                "otpHash", otpHash,
                "passwordHash", passwordHash,
                "attempts", "0"
        );

        redisTemplate.opsForHash().putAll(key, data);

        redisTemplate.expire(key, OTP_TTL);
    }


    public String getOtpHash(UUID customerId) {
    Object value = redisTemplate.opsForHash()
            .get("customer:registration:" + customerId, "otpHash");

    return value != null ? value.toString() : null;
    }

    public String getPasswordHash(UUID customerId) {
    Object value = redisTemplate.opsForHash()
            .get("customer:registration:" + customerId, "passwordHash");

    return value != null ? value.toString() : null;
    }

    public Long incrementAttempts(UUID customerId) {
    return redisTemplate.opsForHash()
            .increment("customer:registration:" + customerId, "attempts", 1);
    }

    public void delete(UUID customerId) {
    redisTemplate.delete("customer:registration:" + customerId);
    }
}