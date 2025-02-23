package com.example.ecommercebasic.controller.product.attribute;

import com.example.ecommercebasic.dto.product.attribute.ProductAttributeRequestDto;
import com.example.ecommercebasic.entity.product.attribute.ProductAttribute;
import com.example.ecommercebasic.service.product.attribute.ProductAttributeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product-attribute")
public class ProductAttributeController {
    private final ProductAttributeService productAttributeService;

    public ProductAttributeController(ProductAttributeService productAttributeService) {
        this.productAttributeService = productAttributeService;
    }

    @PostMapping
    public ResponseEntity<ProductAttribute> create(@RequestBody ProductAttributeRequestDto productAttributeRequestDto) {
        return new ResponseEntity<>(productAttributeService.save(productAttributeRequestDto), HttpStatus.CREATED);
    }
}
