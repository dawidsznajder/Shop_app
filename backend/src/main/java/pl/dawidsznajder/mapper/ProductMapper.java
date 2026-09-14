package pl.dawidsznajder.mapper;

import pl.dawidsznajder.product.Product;
import pl.dawidsznajder.product.dto.ProductResponseDTO;

public class ProductMapper {

    public static ProductResponseDTO toDto(Product product) {

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .imageName(product.getImageName())
                .build();
    }
}
