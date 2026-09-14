package pl.dawidsznajder.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dawidsznajder.common.dto.PageResponseDTO;
import pl.dawidsznajder.mapper.OrderMapper;
import pl.dawidsznajder.order.dto.OrderResponseDTO;

@Tag(
        name = "Orders",
        description = "Operations related to orders (checkout, status, filtering)"
)
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "Checkout cart",
            description = "Creates an order from a shopping cart"
    )
    @PostMapping("/checkout/{cartId}")
    public ResponseEntity<OrderResponseDTO> checkout(
            @Parameter(description = "Cart ID")
            @PathVariable Long cartId) {

        Order order = orderService.checkout(cartId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OrderMapper.toDto(order));
    }

    @Operation(
            summary = "Get order by ID",
            description = "Returns details of a specific order"
    )
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @Parameter(description = "Order ID")
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                orderService.getOrderById(orderId));
    }

    @Operation(
            summary = "Get orders",
            description = "Returns paginated list of orders with optional status filter"
    )
    @GetMapping
    public ResponseEntity<PageResponseDTO<OrderResponseDTO>> getOrders(
            @Parameter(description = "Filter orders by status (PAID, PENDING, etc.")
            @RequestParam(required = false) OrderStatus status,

            @ParameterObject
            Pageable pageable) {

        return ResponseEntity.ok(orderService.getOrders(status, pageable));
    }

    @Operation(
            summary = "Mark order as paid",
            description = "Changes order status from PENDING to PAID"
    )
    @PatchMapping("/{orderId}/pay")
    public ResponseEntity<Void> payOrder(
            @Parameter(description = "Order ID")
            @PathVariable Long orderId) {

        orderService.markAsPaid(orderId);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Mark order as shipped",
            description = "Changes order status from PAID to SHIPPED"
    )
    @PatchMapping("/{orderId}/ship")
    public ResponseEntity<Void> shipOrder(
            @Parameter(description = "Order ID")
            @PathVariable Long orderId) {

        orderService.markAsShipped(orderId);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Mark order as delivered",
            description = "Changes order status from SHIPPED to DELIVERED"
    )
    @PatchMapping("/{orderId}/deliver")
    public ResponseEntity<Void> deliverOrder(
            @Parameter(description = "Order ID")
            @PathVariable Long orderId) {

        orderService.markAsDelivered(orderId);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Cancel order",
            description = "Cancels an existing order"
    )
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(
            @Parameter(description = "Order ID")
            @PathVariable Long orderId) {

        orderService.cancelOrder(orderId);

        return ResponseEntity.noContent().build();
    }
}
