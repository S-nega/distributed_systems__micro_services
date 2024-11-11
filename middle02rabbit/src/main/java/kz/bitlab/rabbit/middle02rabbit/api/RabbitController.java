package kz.bitlab.rabbit.middle02rabbit.api;

import kz.bitlab.rabbit.middle02rabbit.service.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rabbit")
@RequiredArgsConstructor
public class RabbitController {

    private final MessageSender messageSender;

    @PostMapping(value = "/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message){
        try{
            messageSender.sendMessage(message);
            return new ResponseEntity<>("Message send success", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed send message", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
