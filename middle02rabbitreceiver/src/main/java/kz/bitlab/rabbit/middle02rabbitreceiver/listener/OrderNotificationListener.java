package kz.bitlab.rabbit.middle02rabbitreceiver.listener;

import kz.bitlab.rabbit.middle02rabbitreceiver.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderNotificationListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "almaty_orders_queue"),
            exchange = @Exchange(value = "${mq.order.topic.exchange}", type = ExchangeTypes.TOPIC),
            key = "order.#"))
    public void receiveAlmatyOrder(OrderDTO order) {
        log.info("Received order - ORDER:{}", order);
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
