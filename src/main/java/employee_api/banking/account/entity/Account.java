package employee_api.banking.account.entity;

import employee_api.banking.customer.entity.Customer;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bank_accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 30)
    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType accountType;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();

        if (balance == null) {
            balance = BigDecimal.ZERO;
        }

        if (status == null) {
            status = AccountStatus.ACTIVE;
        }
    }

    // Getters and Setters

    public UUID getId() {
    return id;
}

public String getAccountNumber() {
    return accountNumber;
}

public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
}

public Customer getCustomer() {
    return customer;
}

public void setCustomer(Customer customer) {
    this.customer = customer;
}

public AccountType getAccountType() {
    return accountType;
}

public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
}

public BigDecimal getBalance() {
    return balance;
}

public void setBalance(BigDecimal balance) {
    this.balance = balance;
}

public String getCurrency() {
    return currency;
}

public void setCurrency(String currency) {
    this.currency = currency;
}

public AccountStatus getStatus() {
    return status;
}

public void setStatus(AccountStatus status) {
    this.status = status;
}

public LocalDateTime getCreatedAt() {
    return createdAt;
}
}