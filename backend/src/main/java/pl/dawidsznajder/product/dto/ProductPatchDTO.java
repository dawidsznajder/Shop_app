package pl.dawidsznajder.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductPatchDTO {
    private String name;
    private BigDecimal price;
    private String description;
}
