package kz.bitlab.rabbit.middle02rabbit.service;

import kz.bitlab.rabbit.middle02rabbit.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${mq.message.topic.exchange}")
    private String messageTopicExchange;

    public void sendMessage(String message){
        rabbitTemplate.convertAndSend("message-exchange", "key123", message);
    }

    public void sendData(Message message, String department){
        String routingKey = "department." + department;
        rabbitTemplate.convertAndSend(messageTopicExchange, routingKey, message);
    }

}