package pl.dawidsznajder.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dawidsznajder.cart.Cart;
import pl.dawidsznajder.cart.CartItem;
import pl.dawidsznajder.cart.CartItemRepository;
import pl.dawidsznajder.cart.CartRepository;
import pl.dawidsznajder.exception.CartNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Order checkout(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));

        Order order = Order.builder()
                .user(cart.getUser())
                .createdAt(LocalDateTime.now())
                .items(new ArrayList<>())
                .build();

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productName(cartItem.getProduct().getName())
                    .productPrice(cartItem.getProduct().getPrice())
                    .quantity(cartItem.getQuantity())
                    .build();

            order.getItems().add(orderItem);

            BigDecimal itemTotal = cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            totalPrice = totalPrice.add(itemTotal);
        }
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();

        cartRepository.save(cart);

        return savedOrder;
    }
    }
