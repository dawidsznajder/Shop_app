package pl.dawidsznajder.cart.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CartItemResponseDTO {

    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal lineTotal;
    private String imageName;
}
