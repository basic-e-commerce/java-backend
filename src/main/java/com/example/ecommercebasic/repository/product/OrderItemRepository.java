package com.example.ecommercebasic.repository.product;

import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.entity.product.order.OrderItem;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, JpaSpecificationExecutor<OrderItem> {
    List<OrderItem> findAllByProduct(Product product);
}
