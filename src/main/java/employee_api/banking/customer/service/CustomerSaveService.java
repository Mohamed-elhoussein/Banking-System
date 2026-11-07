
package employee_api.banking.customer.service;

import employee_api.banking.customer.entity.Customer;
import employee_api.banking.customer.repository.CustomerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerSaveService {

    private final CustomerRepository customerRepository;

    public CustomerSaveService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Customer save(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }
}
