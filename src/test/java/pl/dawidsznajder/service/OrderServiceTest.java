package pl.dawidsznajder.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.dawidsznajder.cart.Cart;
import pl.dawidsznajder.cart.CartItem;
import pl.dawidsznajder.cart.CartRepository;
import pl.dawidsznajder.exception.CartNotFoundException;
import pl.dawidsznajder.order.Order;
import pl.dawidsznajder.order.OrderRepository;
import pl.dawidsznajder.order.OrderServiceImpl;
import pl.dawidsznajder.order.OrderStatus;
import pl.dawidsznajder.product.Product;
import pl.dawidsznajder.user.User;
import pl.dawidsznajder.user.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

@Mock
private OrderRepository orderRepository;

@Mock
private CartRepository cartRepository;

@Mock
private UserRepository userRepository;

@InjectMocks
private OrderServiceImpl orderService;

@Test
    void shouldCreateOrderFromCart() {

    Long cartId = 1L;

    User user = User.builder()
            .id(1L)
            .build();

    Cart cart = Cart.builder()
            .id(cartId)
            .user(user)
            .items(new ArrayList<>(List.of(
                    CartItem.builder()
                            .quantity(2)
                            .product(Product.builder()
                                    .name("MacBook Air")
                                    .price(BigDecimal.valueOf(3500))
                                    .build())
                            .build()
            )))
            .build();

    when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
    when(orderRepository.save(any(Order.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

    Order result = orderService.checkout(cartId);

    assertNotNull(result);
    assertEquals(user.getId(), result.getUser().getId());
    assertEquals(OrderStatus.PENDING, result.getStatus());
}

@Test
    void shouldThrowExceptionWhenCartNotFound() {

    when(cartRepository.findById(99L))
            .thenReturn(Optional.empty());

    assertThrows(CartNotFoundException.class, () -> {
        orderService.checkout(99L);
    });
}


}
