package com.example.ecommercebasic.repository.product;

import com.example.ecommercebasic.entity.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> existsByNameEqualsIgnoreCase(String name);
    List<Category> findAllByParentCategoryIsNull();
}
