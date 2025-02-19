package com.example.ecommercebasic.service.product.attribute;

import com.example.ecommercebasic.repository.product.attribute.ProductAttributeRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepository;

    public ProductAttributeService(ProductAttributeRepository productAttributeRepository) {
        this.productAttributeRepository = productAttributeRepository;
    }

}
