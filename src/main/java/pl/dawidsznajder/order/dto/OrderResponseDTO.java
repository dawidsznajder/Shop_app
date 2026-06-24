package pl.dawidsznajder.order.dto;

import lombok.Builder;
import lombok.Getter;
import pl.dawidsznajder.order.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResponseDTO {

    private Long orderId;

    private Long userId;

    private LocalDateTime createdAt;

    private BigDecimal totalPrice;

    private List<OrderItemResponseDTO> items;

    private OrderStatus status;
}
