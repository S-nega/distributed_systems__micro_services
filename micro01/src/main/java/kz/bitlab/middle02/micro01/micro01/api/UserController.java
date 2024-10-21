package kz.bitlab.middle02.micro01.micro01.api;

import kz.bitlab.middle02.micro01.micro01.dto.*;
import kz.bitlab.middle02.micro01.micro01.service.UserService;
import kz.bitlab.middle02.micro01.micro01.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
//    @PreAuthorize("hasRole('manager')")
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


    @PostMapping(value = "/create")// "/auth"
    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO userCreateDTO){ // authorize
        return new ResponseEntity<>(userService.createUser(userCreateDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/token")
    public UserTokenDto signIn(@RequestBody UserLoginDto userLoginDto){
        return userService.authenticate(userLoginDto);
    }

    @GetMapping(value = "/current-user-name")
    public String getCurrentUserName(){
        return UserUtils.getCurrentUsername();
    }

    @GetMapping(value = "/current-user")
    public UserDto getCurrentUser(){
        return UserUtils.getCurrentUser();
    }
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordDto userChangePasswordDTO) {
        String currentUsername = UserUtils.getCurrentUsername();

        if(currentUsername == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Could not identify the current user.");
        }

        try {
            userService.changePassword(currentUsername, userChangePasswordDTO.getNewPassword());
            return ResponseEntity.ok("Password changed successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error changing password: " + e.getMessage());
        }
    }
}