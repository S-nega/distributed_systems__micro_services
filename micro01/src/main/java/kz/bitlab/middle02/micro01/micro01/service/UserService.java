package kz.bitlab.middle02.micro01.micro01.service;

import kz.bitlab.middle02.micro01.micro01.client.KeyCloakClient;
import kz.bitlab.middle02.micro01.micro01.dto.*;
import kz.bitlab.middle02.micro01.micro01.entity.Task;
import kz.bitlab.middle02.micro01.micro01.exception.UserNotFoundException;
import kz.bitlab.middle02.micro01.micro01.feign.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KeyCloakClient keyCloakClient;
    private final UserFeignClient userFeignClient;

    public List<UserDto> getUsers() {
        return userFeignClient.getUsers();
    }

    public UserDto getUser(Long id) {
        return userFeignClient.getUser(id);
    }

    public UserDto addUser(UserDto user) {
        return userFeignClient.addUser(user);
    }

    public UserDto createUser(UserCreateDTO userCreateDTO){

        UserRepresentation userRepresentation = keyCloakClient.createUser(userCreateDTO);
        UserDto userDto = new UserDto();
        userDto.setUserName(userRepresentation.getUsername());
        userDto.setEmail(userRepresentation.getEmail());
        userDto.setFirstName(userRepresentation.getFirstName());
        userDto.setLastName(userRepresentation.getLastName());

        return userDto;
//        return userFeignClient.addUser(userDto);
    }

    public UserTokenDto authenticate(UserLoginDto userLoginDto){
        return keyCloakClient.signIn(userLoginDto);
    }

    public UserDto updateUser(UserDto user) {
        return userFeignClient.updateUser(user);
    }

    public void deleteUser(Long id) {
        userFeignClient.deleteUser(id);
    }

    public void changePassword(String userName, String password){
        keyCloakClient.changePassword(userName, password);
    }

}