package employee_api.banking.customer.mapper;

import employee_api.banking.customer.dto.CustomerResponse;
import employee_api.banking.customer.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse toResponse(Customer customer);
}