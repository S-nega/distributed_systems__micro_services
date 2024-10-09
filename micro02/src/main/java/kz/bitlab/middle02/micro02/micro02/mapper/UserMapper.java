package kz.bitlab.middle02.micro02.micro02.mapper;

import kz.bitlab.middle02.micro02.micro02.dto.UserDto;
import kz.bitlab.middle02.micro02.micro02.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    List<UserDto> toDtoList(List<User> users);
}
