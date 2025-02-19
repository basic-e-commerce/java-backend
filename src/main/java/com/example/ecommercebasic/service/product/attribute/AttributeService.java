package com.example.ecommercebasic.service.product.attribute;

import com.example.ecommercebasic.entity.product.Category;
import com.example.ecommercebasic.entity.product.attribute.Attribute;
import com.example.ecommercebasic.repository.product.ProductRepository;
import com.example.ecommercebasic.repository.product.attribute.AttributeRepository;
import com.example.ecommercebasic.repository.product.attribute.AttributeValueRepository;
import com.example.ecommercebasic.service.product.CategoryService;
import com.example.ecommercebasic.service.product.ProductService;
import org.springframework.stereotype.Service;

@Service
public class AttributeService {
    private final AttributeRepository attributeRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final CategoryService categoryService;

    public AttributeService(AttributeRepository attributeRepository, AttributeValueRepository attributeValueRepository, CategoryService categoryService) {
        this.attributeRepository = attributeRepository;
        this.attributeValueRepository = attributeValueRepository;
        this.categoryService = categoryService;
    }

    public String createAttribute(String attributeName, String attributeValue,int categoryId) {
        Category category = categoryService.findById(categoryId);


        return null;
    }
}
