package kz.bitlab.middle02.micro01.micro01.service;

import kz.bitlab.middle02.micro01.micro01.dto.TaskDto;
import kz.bitlab.middle02.micro01.micro01.dto.UserDto;
import kz.bitlab.middle02.micro01.micro01.entity.Task;
import kz.bitlab.middle02.micro01.micro01.exception.UserNotFoundException;
import kz.bitlab.middle02.micro01.micro01.feign.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

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

    public UserDto updateUser(UserDto user) {
        return userFeignClient.updateUser(user);
    }

    public void deleteUser(Long id) {
        userFeignClient.deleteUser(id);
    }
}