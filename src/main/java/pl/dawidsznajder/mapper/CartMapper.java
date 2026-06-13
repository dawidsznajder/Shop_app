package pl.dawidsznajder.mapper;

import pl.dawidsznajder.cart.Cart;
import pl.dawidsznajder.cart.dto.CartItemResponseDTO;
import pl.dawidsznajder.cart.dto.CartResponseDTO;

import java.util.List;

public class CartMapper {

    public static CartResponseDTO toDto(Cart cart) {

        List<CartItemResponseDTO> items = cart.getItems()
                .stream()
                .map(item -> CartItemResponseDTO.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .build())
                .toList();

        return CartResponseDTO.builder()
                    .id(cart.getId())
                    .userId(cart.getUser().getId())
                    .items(items)
                    .build();
    }
}
