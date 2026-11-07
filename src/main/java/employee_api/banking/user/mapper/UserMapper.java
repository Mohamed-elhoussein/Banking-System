package employee_api.banking.user.mapper;
import employee_api.banking.user.dto.CustomerUserResponse;
import employee_api.banking.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  CustomerUserResponse toResponse(User user);
}
