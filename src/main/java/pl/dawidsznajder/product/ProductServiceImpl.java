package pl.dawidsznajder.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dawidsznajder.exception.ProductNotFoundException;
import pl.dawidsznajder.mapper.ProductMapper;
import pl.dawidsznajder.product.dto.ProductPatchDTO;
import pl.dawidsznajder.product.dto.ProductRequestDTO;
import pl.dawidsznajder.product.dto.ProductResponseDTO;
import pl.dawidsznajder.product.dto.ProductUpdateDTO;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setImageName(dto.getImageName());

        Product saved = productRepository.save(product);

        return ProductMapper.toDto(saved);
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

            return ProductMapper.toDto(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductUpdateDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());

        Product updated = productRepository.save(product);

        return ProductMapper.toDto(updated);
    }

    @Override
    public ProductResponseDTO patchProduct(Long id, ProductPatchDTO dto) {
       Product product = productRepository.findById(id)
               .orElseThrow(() -> new ProductNotFoundException(id));

       if (dto.getName() != null) {
           product.setName(dto.getName());
       }

       if (dto.getPrice() != null) {
           product.setPrice(dto.getPrice());
       }

       if (dto.getDescription() != null) {
           product.setDescription(dto.getDescription());
       }
        Product updated = productRepository.save(product);

       return ProductMapper.toDto(updated);
    }
}

