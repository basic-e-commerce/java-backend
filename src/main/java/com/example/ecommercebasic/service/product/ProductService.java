package com.example.ecommercebasic.service.product;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final CategoryService categoryService;
    private final FileService fileService;

    public ProductService(CategoryService categoryService, FileService fileService) {
        this.categoryService = categoryService;
        this.fileService = fileService;
    }
}
