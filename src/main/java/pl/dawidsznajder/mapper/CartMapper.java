package pl.dawidsznajder.mapper;

import pl.dawidsznajder.cart.Cart;
import pl.dawidsznajder.cart.CartItem;
import pl.dawidsznajder.cart.dto.CartItemResponseDTO;
import pl.dawidsznajder.cart.dto.CartResponseDTO;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class CartMapper {

    public static CartResponseDTO toDto(Cart cart) {

        List<CartItemResponseDTO> items = cart.getItems()
                .stream()
                .sorted(Comparator.comparing(CartItem::getId))
                .map(item -> CartItemResponseDTO.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .price(item.getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .imageName(item.getProduct().getImageName())
                        .lineTotal(
                                item.getProduct()
                                        .getPrice()
                                        .multiply(BigDecimal.valueOf(item.getQuantity()))
                        )
                        .build())
                .toList();

        BigDecimal totalPrice = items.stream()
                .map(CartItemResponseDTO::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponseDTO.builder()
                    .id(cart.getId())
                    .userId(cart.getUser().getId())
                    .items(items)
                    .totalPrice(totalPrice)
                    .build();
    }
}
