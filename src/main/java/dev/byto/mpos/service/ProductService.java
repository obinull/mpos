package dev.byto.mpos.service;

import dev.byto.mpos.dto.ProductDto;
import dev.byto.mpos.entity.Product;
import dev.byto.mpos.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setType(productDto.getType());
        product.setPrice(productDto.getPrice());
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Long id, ProductDto productDto) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDto.getName());
            product.setType(productDto.getType());
            product.setPrice(productDto.getPrice());
            return productRepository.save(product);
        });
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
