package pl.dawidsznajder.cart.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponseDTO {

    private Long productId;
    private String productName;
    private Integer quantity;
}
