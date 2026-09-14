package pl.dawidsznajder.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.dawidsznajder.common.dto.PageResponseDTO;
import pl.dawidsznajder.order.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    Order checkout(Long cartId);

    OrderResponseDTO getOrderById(Long orderId);

    List<OrderResponseDTO> getOrdersByUserId(Long userId);

    void cancelOrder(Long orderId);

    void markAsPaid(Long orderId);

    void markAsShipped(Long orderId);

    void markAsDelivered(Long orderId);

    PageResponseDTO<OrderResponseDTO> getOrders(OrderStatus status, Pageable pageable);
}
