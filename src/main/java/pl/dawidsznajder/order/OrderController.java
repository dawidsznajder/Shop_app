package pl.dawidsznajder.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dawidsznajder.mapper.OrderMapper;
import pl.dawidsznajder.order.dto.OrderResponseDTO;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout/{cartId}")
    public ResponseEntity<OrderResponseDTO> checkout(
            @PathVariable Long cartId) {

        Order order = orderService.checkout(cartId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OrderMapper.toDto(order));
    }
}
