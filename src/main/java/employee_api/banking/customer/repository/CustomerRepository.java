package employee_api.banking.customer.repository;

import employee_api.banking.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByNationalId(String nationalId);

    boolean existsByEmail(String email);

    boolean existsByNationalId(String nationalId);
    boolean existsByCardNumberHash(String cardNumberHash);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Optional<Customer> findByEmailForUpdate(@Param("email") String email);
    
}