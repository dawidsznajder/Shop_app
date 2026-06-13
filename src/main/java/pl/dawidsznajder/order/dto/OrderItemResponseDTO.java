package pl.dawidsznajder.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderItemResponseDTO {

    private String productName;

    private BigDecimal productPrice;

    private Integer quantity;
}
