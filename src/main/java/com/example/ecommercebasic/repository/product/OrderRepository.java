package com.example.ecommercebasic.repository.product;

import com.example.ecommercebasic.entity.payment.Payment;
import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.entity.product.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> , JpaSpecificationExecutor<Order> {
    boolean existsByOrderCode(String orderCode);
    Optional<Order> findByOrderCode(String orderCode);
    Optional<Order> findByPayments(Payment payment);
}
