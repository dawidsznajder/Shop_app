package pl.dawidsznajder.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductUpdateDTO {
    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @NotNull(message = "Price is required!")
    @Positive(message = "Price must be greater than zero!")
    private BigDecimal price;

    @NotBlank(message = "Description cannot be empty!")
    private String description;
}
