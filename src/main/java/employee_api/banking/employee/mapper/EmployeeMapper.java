package employee_api.banking.employee.mapper;

import employee_api.banking.employee.dto.EmployeeResponse;
import employee_api.banking.employee.entity.BankEmployee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeResponse toResponse(BankEmployee employee);
}