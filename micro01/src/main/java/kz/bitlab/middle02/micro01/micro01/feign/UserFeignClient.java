package kz.bitlab.middle02.micro01.micro01.feign;

import kz.bitlab.middle02.micro01.micro01.dto.TaskDto;
import kz.bitlab.middle02.micro01.micro01.dto.UserDto;
import org.apache.catalina.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-feign-client", url = "${feign.user.url}")
public interface UserFeignClient {

    @GetMapping(value = "/user")
    List<UserDto> getUsers();

    @GetMapping(value = "/user/{id}")
    UserDto getUser(@PathVariable(name = "id") Long id);

    @PostMapping(value = "/user")
    UserDto addUser(@RequestBody UserDto user);

    @PutMapping(value = "/user/{id}")
    UserDto updateUser(@RequestBody UserDto user);

    @DeleteMapping(value = "/user/{id}")
    void deleteUser(@PathVariable(name = "id") Long id);

    @PostMapping(value = "/user/create")//
    UserDto createUser(@RequestBody UserDto user);
}