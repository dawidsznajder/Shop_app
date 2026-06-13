package pl.dawidsznajder.cart;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dawidsznajder.cart.dto.AddCartItemRequest;
import pl.dawidsznajder.cart.dto.CartItemUpdateRequest;
import pl.dawidsznajder.cart.dto.CartResponseDTO;
import pl.dawidsznajder.mapper.CartMapper;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartResponseDTO> addProductToCart(
            @PathVariable Long cartId,
            @RequestBody AddCartItemRequest request
            ) {

        Cart cart = cartService.addProductToCart(cartId, request);

        return ResponseEntity.ok(
                CartMapper.toDto(cart)
        );
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponseDTO> getCart(@PathVariable Long cartId) {

        Cart cart = cartService.getCartById(cartId);

        return ResponseEntity.ok(
                CartMapper.toDto(cart)
        );
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> removeProductFromCart(
            @PathVariable Long cartId,
            @PathVariable Long productId) {

        cartService.removeProductFromCart(cartId, productId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> updateCartItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @RequestBody @Valid CartItemUpdateRequest request) {

        cartService.updateCartItemQuantity(
                cartId,
                productId,
                request.getQuantity()
        );

        return ResponseEntity.noContent().build();
    }

}
