package pl.dawidsznajder.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dawidsznajder.product.dto.ProductPatchDTO;
import pl.dawidsznajder.product.dto.ProductRequestDTO;
import pl.dawidsznajder.product.dto.ProductResponseDTO;
import pl.dawidsznajder.product.dto.ProductUpdateDTO;
import java.util.List;

@Tag(
        name = "Products",
        description = "Product catalog operations"
)
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(
            summary = "Create product",
            description = "Adds a new product to the catalog"
    )
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(dto));
    }

    @Operation(
            summary = "Get product by ID",
            description = "Returns details of a specific product"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @Parameter(description = "Product ID")
            @PathVariable Long id) {
        return ResponseEntity.ok(
                productService.getProductById(id));
    }

    @Operation(
            summary = "Get all products",
            description = "Returns a list of all available products"
    )
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products =
                productService.getAllProducts();

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Update product",
            description = "Updates all product fields and returns updated product details"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @Parameter(description = "Product ID")
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateDTO dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @Operation(
            summary = "Partially update product",
            description = "Updates selected product fields and returns updated product details"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> patchProduct(
            @Parameter(description = "Product ID")
            @PathVariable Long id,
            @RequestBody ProductPatchDTO dto) {
        return ResponseEntity.ok(productService.patchProduct(id, dto));
    }

    @Operation(
            summary = "Delete product",
            description = "Removes a product from the catalog"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(
            @Parameter(description = "Product ID")
            @PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
