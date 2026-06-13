package pl.dawidsznajder.cart;

import pl.dawidsznajder.cart.dto.AddCartItemRequest;
import pl.dawidsznajder.cart.dto.CartResponseDTO;

public interface CartService {

    Cart createCartForUser(Long userId);

    Cart addProductToCart(Long cartId, AddCartItemRequest request);

    Cart getCartById(Long cartId);

    void removeProductFromCart(Long cartId, Long productId);

    void updateCartItemQuantity(Long cartId, Long productId, Integer quantity);
}
