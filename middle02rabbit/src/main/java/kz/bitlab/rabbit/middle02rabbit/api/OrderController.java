package kz.bitlab.rabbit.middle02rabbit.api;

import kz.bitlab.rabbit.middle02rabbit.dto.OrderDTO;
import kz.bitlab.rabbit.middle02rabbit.service.OrderPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderPublisher orderPublisher;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            orderPublisher.sendOrderToPrepare(orderDTO);
            return new ResponseEntity<>("Order created", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Order failed to create", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{status}")
    public ResponseEntity<String> updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable String status) {
        try {
            orderPublisher.updateOrderStatus(orderDTO, status);
            return new ResponseEntity<>("Order updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Order failed to updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
