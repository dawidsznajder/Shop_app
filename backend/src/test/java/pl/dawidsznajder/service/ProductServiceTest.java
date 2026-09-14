package pl.dawidsznajder.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.dawidsznajder.product.Product;
import pl.dawidsznajder.product.ProductRepository;
import pl.dawidsznajder.product.ProductServiceImpl;
import pl.dawidsznajder.product.dto.ProductRequestDTO;
import pl.dawidsznajder.product.dto.ProductResponseDTO;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldCreateProduct() {

        ProductRequestDTO request = ProductRequestDTO.builder()
                .name("MacBook Air")
                .price(BigDecimal.valueOf(5000))
                .description("Laptop Apple")
                .build();

        Product savedProduct = Product.builder()
                .id(1L)
                .name("MacBook Air")
                .price(BigDecimal.valueOf(5000))
                .description("Laptop Apple")
                .build();

        when(productRepository.save(any(Product.class)))
                .thenReturn(savedProduct);

        ProductResponseDTO result = productService.createProduct(request);

        assertEquals("MacBook Air", result.getName());
        assertEquals(BigDecimal.valueOf(5000), result.getPrice());
    }
}
