package employee_api.banking.account.service;

import employee_api.banking.account.dto.CreateAccountRequest;
import employee_api.banking.account.entity.Account;
import employee_api.banking.account.entity.AccountStatus;
import employee_api.banking.account.repository.AccountRepository;
import employee_api.banking.customer.entity.Customer;
import employee_api.banking.customer.repository.CustomerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(
            AccountRepository accountRepository,
            CustomerRepository customerRepository
    ) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Account createAccount(CreateAccountRequest request) {

        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Customer not found"
                ));

        Account account = new Account();

        account.setCustomer(customer);
        account.setAccountType(request.accountType());
        account.setCurrency(request.currency());
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.ACTIVE);

        account.setAccountNumber(generateAccountNumber());

        return accountRepository.save(account);
    }

    private String generateAccountNumber() {

        String accountNumber;

        do {
            accountNumber = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 16)
                    .toUpperCase();

        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
}