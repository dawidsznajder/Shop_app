package pl.dawidsznajder.mapper;

import pl.dawidsznajder.order.Order;
import pl.dawidsznajder.order.dto.OrderItemResponseDTO;
import pl.dawidsznajder.order.dto.OrderResponseDTO;

import java.util.List;

public class OrderMapper {

    public static OrderResponseDTO toDto(Order order) {

        List<OrderItemResponseDTO> items = order.getItems()
                .stream()
                .map(item -> OrderItemResponseDTO.builder()
                        .productName(item.getProductName())
                        .productPrice(item.getProductPrice())
                        .quantity(item.getQuantity())
                        .build())
                .toList();

        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .userId(order.getUser().getId())
                .createdAt(order.getCreatedAt())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .items(items)
                .build();
    }
}
