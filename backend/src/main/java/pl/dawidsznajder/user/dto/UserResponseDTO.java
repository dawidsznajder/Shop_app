package pl.dawidsznajder.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;
}
