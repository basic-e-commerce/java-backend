package com.example.ecommercebasic.repository.product;

import com.example.ecommercebasic.entity.product.Category;
import com.example.ecommercebasic.entity.product.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> existsByNameEqualsIgnoreCase(String name);
    List<Category> findAllByParentCategoryIsNull();
    @Query(value = "SELECT * FROM category WHERE is_sub_category = true", nativeQuery = true)
    List<Category> findSubCategories();


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM category_product  WHERE category_id = :categoryId",nativeQuery = true)
    void deleteCategoryProductRelations(@Param("categoryId") int categoryId);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO category_product (category_id, product_id) VALUES (:categoryId, :productId)", nativeQuery = true)
    void addProductToCategory(@Param("categoryId") Integer categoryId, @Param("productId") Integer productId);

    @Query(value = "SELECT p.* FROM product p " +
            "JOIN category_product cp ON p.id = cp.product_id " +
            "WHERE cp.category_id = :categoryId", nativeQuery = true)
    Set<Product> findProductsByCategoryId(@Param("categoryId") Integer categoryId);


    Optional<Category> findByNameEqualsIgnoreCase(String name);
}
