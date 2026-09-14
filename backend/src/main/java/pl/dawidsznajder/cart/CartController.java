package pl.dawidsznajder.cart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dawidsznajder.cart.dto.AddCartItemRequest;
import pl.dawidsznajder.cart.dto.CartItemUpdateRequest;
import pl.dawidsznajder.cart.dto.CartResponseDTO;
import pl.dawidsznajder.mapper.CartMapper;

@Tag(
        name = "Cart",
        description = "Shopping cart operations"
)
@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Operation(
            summary = "Get cart",
            description = "Returns cart details with all cart items"
    )
    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponseDTO> getCart(
            @Parameter(description = "Cart ID")
            @PathVariable Long cartId) {

        Cart cart = cartService.getCartById(cartId);

        return ResponseEntity.ok(
                CartMapper.toDto(cart)
        );
    }

    @Operation(
            summary = "Add product to cart",
            description = "Adds a product to a shopping cart"
    )
    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartResponseDTO> addProductToCart(
            @Parameter(description = "Cart ID")
            @PathVariable Long cartId,
            @RequestBody AddCartItemRequest request
            ) {

        Cart cart = cartService.addProductToCart(cartId, request);

        return ResponseEntity.ok(
                CartMapper.toDto(cart)
        );
    }

    @Operation(
            summary = "Update product quantity",
            description = "Updates a quantity of a product in a shopping cart"
    )
    @PatchMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> updateCartItemQuantity(
            @Parameter(description = "Cart ID")
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

    @Operation(
            summary = "Remove product from cart",
            description = "Removes a product from a shopping cart"
    )
    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> removeProductFromCart(
            @Parameter(description = "Cart ID")
            @PathVariable Long cartId,
            @PathVariable Long productId) {

        cartService.removeProductFromCart(cartId, productId);

        return ResponseEntity.noContent().build();
    }
}
