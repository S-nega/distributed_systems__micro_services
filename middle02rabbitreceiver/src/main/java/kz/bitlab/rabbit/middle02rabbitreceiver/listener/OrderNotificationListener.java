package kz.bitlab.rabbit.middle02rabbitreceiver.listener;

import kz.bitlab.rabbit.middle02rabbitreceiver.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderNotificationListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "almaty_orders_queue",
                    arguments = {
                            @Argument(name = "x-dead-letter-exchange", value = "dlx"),
                            @Argument(name = "x-dead-letter-routing-key", value = "dlx.almaty_orders")
                    }),
            exchange = @Exchange(value = "${mq.order.topic.exchange}", type = ExchangeTypes.TOPIC),
            key = "order.#"))
    public void receiveAlmatyOrder(OrderDTO order) {
        try {
            log.info("Received order - ORDER:{}", order);
//            processOrder(order);
        } catch (Exception e) {
            log.error("Error processing order - ORDER:{}, ERROR:{}", order, e.getMessage());
            throw e;
        }
    }

    private void processOrder(OrderDTO order) {
        if (someConditionFails()) { // ИМИТАЦИЯ ОШИБКИ
            throw new RuntimeException("Failed to process order");
        }
        // обработка запроса order
    }

    private boolean someConditionFails() {
        return true;
    }

    // Listener для клиента обновления статусов заказов
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "customer_updates_queue"),
            exchange = @Exchange(value = "${mq.order.fanout.exchange}", type = ExchangeTypes.FANOUT),
            key = ""))
    public void receiveCustomerOrderStatusUpdate(OrderDTO order) {
        log.info("Customer notification - ORDER:{} status updated: {}", order, order.getStatus());
    }

    // Listener для курьеров: обновления статусов заказов
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "courier_updates_queue"),
            exchange = @Exchange(value = "${mq.order.fanout.exchange}", type = ExchangeTypes.FANOUT),
            key = ""))
    public void receiveCourierOrderStatusUpdate(OrderDTO order) {
        log.info("Courier notification - ORDER:{} status updated: {}", order, order.getStatus());
    }
}
