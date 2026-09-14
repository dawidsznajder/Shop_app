package pl.dawidsznajder.user;

import pl.dawidsznajder.user.dto.UserRequestDTO;
import pl.dawidsznajder.user.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO requestDTO);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    void deleteUser(Long id);
}
