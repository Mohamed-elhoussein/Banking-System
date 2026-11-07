package employee_api.banking.employee.repository;

import employee_api.banking.employee.entity.BankEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BankEmployeeRepository
        extends JpaRepository<BankEmployee, UUID> {

    Optional<BankEmployee> findByEmail(String email);

    boolean existsByEmail(String email);

}