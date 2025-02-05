package com.example.ecommercebasic.repository.product;

import com.example.ecommercebasic.entity.product.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> existsByNameEqualsIgnoreCase(String name);
    List<Category> findAllByParentCategoryIsNull();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM category_product  WHERE category_id = :categoryId",nativeQuery = true)
    void deleteCategoryProductRelations(@Param("categoryId") int categoryId);
}
