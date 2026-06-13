package pl.dawidsznajder.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dawidsznajder.cart.Cart;
import pl.dawidsznajder.cart.CartService;
import pl.dawidsznajder.cart.dto.CartResponseDTO;
import pl.dawidsznajder.mapper.CartMapper;
import pl.dawidsznajder.user.dto.UserRequestDTO;
import pl.dawidsznajder.user.dto.UserResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO requestDTO) {

        UserResponseDTO createdUser = userService.createUser(requestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }

    @PostMapping("/{userId}/cart")
    public ResponseEntity<CartResponseDTO> createCart(
            @PathVariable Long userId) {

        Cart cart = cartService.createCartForUser(userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CartMapper.toDto(cart));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
