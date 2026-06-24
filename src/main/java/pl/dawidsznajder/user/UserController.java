package pl.dawidsznajder.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dawidsznajder.cart.Cart;
import pl.dawidsznajder.cart.CartService;
import pl.dawidsznajder.cart.dto.CartResponseDTO;
import pl.dawidsznajder.mapper.CartMapper;
import pl.dawidsznajder.order.OrderService;
import pl.dawidsznajder.order.dto.OrderResponseDTO;
import pl.dawidsznajder.user.dto.UserRequestDTO;
import pl.dawidsznajder.user.dto.UserResponseDTO;

import java.util.List;

@Tag(
        name = "Users",
        description = "User management operations"
)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;

    @Operation(
            summary = "Create user",
            description = "Creates a new user account"
    )
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO requestDTO) {

        UserResponseDTO createdUser = userService.createUser(requestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }

    @Operation(
            summary = "Create cart for user",
            description = "Creates a shopping cart assigned to a specific user"
    )
    @PostMapping("/{userId}/cart")
    public ResponseEntity<CartResponseDTO> createCart(
            @Parameter(description = "User ID")
            @PathVariable Long userId) {

        Cart cart = cartService.createCartForUser(userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CartMapper.toDto(cart));
    }

    @Operation(
            summary = "Get user by ID",
            description = "Returns details of a specific user"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "User ID")
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.getUserById(id));
    }

    @Operation(
            summary = "Get user orders",
            description = "Returns all orders placed by a specific user"
    )
    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(
            @Parameter(description = "User ID")
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                orderService.getOrdersByUserId(userId));
    }

    @Operation(
            summary = "Get all users",
            description = "Returns a list of all users"
    )
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Delete user",
            description = "Deletes a user by ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(
            @Parameter(description = "User ID")
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
