package pl.dawidsznajder.exception;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(Long id) {
        super("Cart with id " + id + " not found!");
    }
}
