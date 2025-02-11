package com.example.ecommercebasic.repository.product;

import com.example.ecommercebasic.entity.product.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
