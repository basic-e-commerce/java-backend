package com.example.ecommercebasic.repository.product.attribute;

import com.example.ecommercebasic.entity.product.attribute.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {
}
