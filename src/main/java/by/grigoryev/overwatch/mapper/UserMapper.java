package by.grigoryev.overwatch.mapper;

import by.grigoryev.overwatch.dto.UserDto;
import by.grigoryev.overwatch.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

}
