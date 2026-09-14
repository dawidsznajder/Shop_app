package pl.dawidsznajder.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dawidsznajder.cart.dto.AddCartItemRequest;
import pl.dawidsznajder.exception.CartNotFoundException;
import pl.dawidsznajder.exception.ProductNotFoundException;
import pl.dawidsznajder.exception.UserNotFoundException;
import pl.dawidsznajder.product.Product;
import pl.dawidsznajder.product.ProductRepository;
import pl.dawidsznajder.user.User;
import pl.dawidsznajder.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    public Cart createCartForUser(Long userId) {

       User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

       if (user.getCart() != null) {
           throw new IllegalStateException("User already has a cart");
       }

        Cart cart = Cart.builder()
                .user(user)
                .build();

            user.setCart(cart);

            return cartRepository.save(cart);
    }

    @Override
    public Cart addProductToCart(Long cartId, AddCartItemRequest request) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(request.getProductId()));

        Optional<CartItem> existingCartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingCartItem.isPresent()) {

            CartItem cartItem = existingCartItem.get();

            cartItem.setQuantity(
                    cartItem.getQuantity() + request.getQuantity()
            );

            cartItemRepository.save(cartItem);

        } else {

            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();

            cartItemRepository.save(cartItem);

            cart.getItems().add(cartItem);
        }

        return cart;
    }

    @Override
    public Cart getCartById(Long cartId) {

       return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));
    }

    @Override
    public void removeProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));

        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(productId));

        cart.getItems().remove(cartItem);

        cartItemRepository.delete(cartItem);
    }

    @Override
    public void updateCartItemQuantity(Long cartId, Long productId, Integer quantity) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));

        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(productId));

        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);
    }
}
