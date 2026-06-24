package pl.dawidsznajder.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.dawidsznajder.cart.Cart;
import pl.dawidsznajder.cart.CartItem;
import pl.dawidsznajder.cart.CartItemRepository;
import pl.dawidsznajder.cart.CartRepository;
import pl.dawidsznajder.common.dto.PageResponseDTO;
import pl.dawidsznajder.exception.CartNotFoundException;
import pl.dawidsznajder.exception.EmptyCartException;
import pl.dawidsznajder.exception.OrderNotFoundException;
import pl.dawidsznajder.exception.UserNotFoundException;
import pl.dawidsznajder.mapper.OrderMapper;
import pl.dawidsznajder.order.dto.OrderResponseDTO;

import pl.dawidsznajder.user.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    public Order checkout(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException();
        }

        Order order = Order.builder()
                .user(cart.getUser())
                .createdAt(LocalDateTime.now())
                .status(OrderStatus.PENDING)
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

    @Override
    public OrderResponseDTO getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        return OrderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Override
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (order.getStatus() == OrderStatus.DELIVERED
        || order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Order cannot be cancelled"
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }

    @Override
    public void markAsPaid(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException(
                    "Only PENDING orders can be paid"
            );
        }

        order.setStatus(OrderStatus.PAID);

        orderRepository.save(order);
    }

    @Override
    public void markAsShipped(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (order.getStatus() != OrderStatus.PAID) {
            throw new IllegalStateException(
                    "Only PAID orders can be shipped"
            );
        }

        order.setStatus(OrderStatus.SHIPPED);

        orderRepository.save(order);
    }

    @Override
    public void markAsDelivered(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (order.getStatus() != OrderStatus.SHIPPED) {
            throw new IllegalStateException(
                    "Only SHIPPED orders can be delivered"
            );
        }

        order.setStatus(OrderStatus.DELIVERED);

        orderRepository.save(order);
    }

    @Override
    public PageResponseDTO<OrderResponseDTO> getOrders(OrderStatus status, Pageable pageable) {

        Page<Order> page = (status != null)
                ? orderRepository.findByStatus(status, pageable)
                : orderRepository.findAll(pageable);

        Page<OrderResponseDTO> dtoPage = page.map(OrderMapper::toDto);

        return PageResponseDTO.<OrderResponseDTO>builder()
                .content(dtoPage.getContent())
                .page(dtoPage.getNumber())
                .size(dtoPage.getSize())
                .totalElements(dtoPage.getTotalElements())
                .totalPages(dtoPage.getTotalPages())
                .build();
    }
}
