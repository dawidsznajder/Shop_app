package pl.dawidsznajder.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductRequestDTO {
    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @NotNull(message = "Price is required!")
    @Positive(message = "Price must be greater than 0!")
    private BigDecimal price;

    @NotBlank(message = "Description cannot be empty!")
    private String description;
}
