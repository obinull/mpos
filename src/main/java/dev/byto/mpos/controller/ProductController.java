package dev.byto.mpos.controller;

import dev.byto.mpos.dto.ApiResponse;
import dev.byto.mpos.dto.ProductDto;
import dev.byto.mpos.entity.Product;
import dev.byto.mpos.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Product>>> getAllProducts(Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);
        ApiResponse<Page<Product>> response = new ApiResponse<>("SUCCESS", "Products retrieved successfully", products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> {
                    ApiResponse<Product> response = new ApiResponse<>("SUCCESS", "Product found successfully", product);
                    return new ResponseEntity<ApiResponse<?>>(response, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    ApiResponse<Object> response = new ApiResponse<>("FAILED", "Product with id " + id + " not found.", null);
                    return new ResponseEntity<ApiResponse<?>>(response, HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody ProductDto productDto) {
        Product createdProduct = productService.createProduct(productDto);
        ApiResponse<Product> response = new ApiResponse<>("SUCCESS", "Product created successfully", createdProduct);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto)
                .map(product -> {
                    ApiResponse<Product> response = new ApiResponse<>("SUCCESS", "Product updated successfully", product);
                    return new ResponseEntity<ApiResponse<?>>(response, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    ApiResponse<Object> response = new ApiResponse<>("FAILED", "Product with id " + id + " not found for update.", null);
                    return new ResponseEntity<ApiResponse<?>>(response, HttpStatus.NOT_FOUND);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            ApiResponse<Object> response = new ApiResponse<>("SUCCESS", "Product with id " + id + " deleted successfully.", null);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Object> response = new ApiResponse<>("FAILED", "Product with id " + id + " not found for deletion.", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
