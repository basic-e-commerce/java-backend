package com.example.ecommercebasic.repository.product;

import com.example.ecommercebasic.entity.product.Category;
import com.example.ecommercebasic.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findAllByCategories(Category category);
}
