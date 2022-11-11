package by.grigoryev.cryptocurrency.mapper;

import by.grigoryev.cryptocurrency.dto.UserDto;
import by.grigoryev.cryptocurrency.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

}
