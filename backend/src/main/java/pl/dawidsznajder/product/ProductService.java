package pl.dawidsznajder.product;

import pl.dawidsznajder.product.dto.ProductPatchDTO;
import pl.dawidsznajder.product.dto.ProductRequestDTO;
import pl.dawidsznajder.product.dto.ProductResponseDTO;
import pl.dawidsznajder.product.dto.ProductUpdateDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO dto);

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> getAllProducts();

    void deleteProduct(Long id);

    ProductResponseDTO updateProduct(Long id, ProductUpdateDTO dto);

    ProductResponseDTO patchProduct(Long id, ProductPatchDTO dto);
}