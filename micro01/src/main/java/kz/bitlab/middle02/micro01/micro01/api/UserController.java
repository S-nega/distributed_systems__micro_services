package kz.bitlab.middle02.micro01.micro01.api;

import kz.bitlab.middle02.micro01.micro01.dto.TaskDto;
import kz.bitlab.middle02.micro01.micro01.dto.UserDto;
import kz.bitlab.middle02.micro01.micro01.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(value = "{id}")
    public UserDto getUser(@PathVariable(name = "id") Long id){
        return userService.getUser(id);
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto user){
        return userService.addUser(user);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto user){
        return userService.updateUser(user);
    }

    @DeleteMapping(value = "{id}") // (value = "/{id}")
    public void deleteUser(@PathVariable(name = "id") Long id){
        userService.deleteUser(id);
    }
}