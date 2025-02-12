package com.example.ecommercebasic.repository.product;

import com.example.ecommercebasic.entity.product.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByOrderCode(String orderCode);
    Optional<Order> findByOrderCode(String orderCode);
}
