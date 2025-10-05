package dev.byto.mpos.repository;

import dev.byto.mpos.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
