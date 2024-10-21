package kz.bitlab.middle02.micro01.micro01.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping(value = "/user-list")
    public List<String> userList(){
        return Arrays.asList("Snega", "Alex", "Max");
    }

}
