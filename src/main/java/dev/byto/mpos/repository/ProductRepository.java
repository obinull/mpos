package dev.byto.mpos.repository;

import dev.byto.mpos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}