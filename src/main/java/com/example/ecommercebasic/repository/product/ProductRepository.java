package com.example.ecommercebasic.repository.product;

import com.example.ecommercebasic.entity.product.Category;
import com.example.ecommercebasic.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findAllByCategoriesAndStatus(Category category, boolean status);
    List<Product> findAllByCategories(Category category);
    Optional<Product> findByProductNameEqualsIgnoreCase(String productName);
    boolean existsByProductNameEqualsIgnoreCase(String productName);

}
