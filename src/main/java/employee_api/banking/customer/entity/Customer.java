package employee_api.banking.customer.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import employee_api.banking.user.entity.User;

@Entity
@Table(
    name = "bank_customers",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "national_id"),
        @UniqueConstraint(
            name = "uk_customer_card_number_hash",
            columnNames = "card_number_hash"
        )
    }
)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 20)
    private String nationalId;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 255)
    private String cardNumberHash;

      @Column(length = 4)
      private String cardLastFour;

      @Column(length = 150)
      private String cardHolderName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
    return createdAt;
    }
    // Getters and Setters

        public UUID getId() {
        return id;
    }

    public String getCardNumberHash() {
        return cardNumberHash;
    }

    public void setCardNumberHash(String cardNumberHash) {
        this.cardNumberHash = cardNumberHash;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardLastFour() {
        return cardLastFour;
    }

    public void setCardLastFour(String cardLastFour) {
        this.cardLastFour = cardLastFour;
    }




    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}