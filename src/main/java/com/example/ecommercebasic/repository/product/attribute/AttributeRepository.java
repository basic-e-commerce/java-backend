package com.example.ecommercebasic.repository.product.attribute;

import com.example.ecommercebasic.entity.product.attribute.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
}
