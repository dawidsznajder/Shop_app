package pl.dawidsznajder.order;

public interface OrderService {

    Order checkout(Long cartId);
}
