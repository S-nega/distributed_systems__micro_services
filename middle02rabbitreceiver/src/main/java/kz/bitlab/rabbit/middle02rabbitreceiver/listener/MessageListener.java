package kz.bitlab.rabbit.middle02rabbitreceiver.listener;

import kz.bitlab.rabbit.middle02rabbitreceiver.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {

    @RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = "message-exchange",
                type = ExchangeTypes.DIRECT),
        value = @Queue(value = "message-queue"), key = "key123"))
    public void receivedMessage(String message) {
        log.info("Received message: {}", message);
    }

//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "department_messages_queue",
//                arguments = {
//                    @Argument(name = "x-dead-letter-exchange", value = "dlx"),
//                    @Argument(name = "x-dead-letter-routing-key", value = "dlx.department_queue")
//            }
//            ),
////            exchange = @Exchange(value = "${mq.message.topic.exchange}", type = ExchangeTypes.TOPIC),
//            key = "department.#"
//    ))
//    public void receiveMessageFromDepartment(Message message){
//        try{
//            log.info("Received message - MESSAGE: {}", message.getTitle());
//            processMessage(message);
//        }catch (Exception e){
//            log.error("Error on processing Message {}", message.getTitle());
//            throw e;
//        }
//        log.info(message.getTitle(), message.getContent());
//    }

    private void processMessage(Message message){
        log.error("Error is happening {}", message.getTitle());
        throw new RuntimeException("Failed to process message");
    }
}
