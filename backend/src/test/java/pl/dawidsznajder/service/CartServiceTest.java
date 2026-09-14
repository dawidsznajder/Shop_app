package pl.dawidsznajder.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.dawidsznajder.cart.Cart;
import pl.dawidsznajder.cart.CartItemRepository;
import pl.dawidsznajder.cart.CartRepository;
import pl.dawidsznajder.cart.CartServiceImpl;
import pl.dawidsznajder.cart.dto.AddCartItemRequest;
import pl.dawidsznajder.product.Product;
import pl.dawidsznajder.product.ProductRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void shouldAddProductToCart() {

        Long cartId = 1L;
        Long productId = 2L;

        Cart cart = Cart.builder()
                .id(cartId)
                .items(new ArrayList<>())
                .build();

        Product product = Product.builder()
                .id(productId)
                .name("iPhone 14")
                .build();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        AddCartItemRequest request = new AddCartItemRequest();

        request.setProductId(productId);
        request.setQuantity(2);

        cartService.addProductToCart(cartId, request);

        assertEquals(1, cart.getItems().size());
    }
}
