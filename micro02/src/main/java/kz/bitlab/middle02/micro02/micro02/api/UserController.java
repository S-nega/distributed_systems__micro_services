package kz.bitlab.middle02.micro02.micro02.api;

import kz.bitlab.middle02.micro02.micro02.dto.UserDto;
import kz.bitlab.middle02.micro02.micro02.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(value = "{id}")
    public UserDto getUser(@PathVariable(name = "id") Long id) {
        return userService.getUser(id);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto user){
        return userService.updateUser(user);
    }

    @DeleteMapping(value = "{id}") // (value = "/{id}")
    public void deleteUser(@PathVariable(name = "id") Long id){
        userService.deleteUser(id);
    }


    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.OK);
    }
}
