package pl.dawidsznajder.user.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank(message = "First name cannot be empty!")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty!")
    private String lastName;

    @Email(message = "Email should be valid!")
    @NotBlank(message = "Email cannot be empty!")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password cannot be empty!")
    private String password;
}
