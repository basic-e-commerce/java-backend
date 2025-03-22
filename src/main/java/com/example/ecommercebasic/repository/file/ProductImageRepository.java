package com.example.ecommercebasic.repository.file;

import com.example.ecommercebasic.entity.file.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
