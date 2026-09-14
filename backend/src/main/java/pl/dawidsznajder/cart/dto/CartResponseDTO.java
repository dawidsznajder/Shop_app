package pl.dawidsznajder.cart.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class CartResponseDTO {

    private Long id;
    private Long userId;
    private List<CartItemResponseDTO> items;
    private BigDecimal totalPrice;
}
