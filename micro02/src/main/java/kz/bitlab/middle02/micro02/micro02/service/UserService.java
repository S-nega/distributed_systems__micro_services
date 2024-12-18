package kz.bitlab.middle02.micro02.micro02.service;

import kz.bitlab.middle02.micro02.micro02.dto.UserDto;
import kz.bitlab.middle02.micro02.micro02.mapper.UserMapper;
import kz.bitlab.middle02.micro02.micro02.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    public UserDto getUser(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElse(null));
    }

    public UserDto addUser(UserDto userDto) {

//        UserRepresentation userRepresentation = keyCloakClient.createUser(userCreateDTO);
//        UserDto userDto = new UserDto();
//        userDto.setUserName(userRepresentation.getUsername());
//        userDto.setEmail(userRepresentation.getEmail());
//        userDto.setFirstName(userRepresentation.getFirstName());
//        userDto.setLastName(userRepresentation.getLastName());
//
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));

//        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

//    public UserDto createUser(UserDto userDTO){
//
////        UserRepresentation userRepresentation = keyCloakClient.createUser(userCreateDTO);
//        UserDto userDto = new UserDto();
////        userDto.setUserName(userRepresentation.getUsername());
////        userDto.setEmail(userRepresentation.getEmail());
////        userDto.setFirstName(userRepresentation.getFirstName());
////        userDto.setLastName(userRepresentation.getLastName());
//
//        return userDto;
//        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
//    }

    public UserDto updateUser(UserDto userDto) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }
}
