package com.example.ecommercebasic.controller.product;

import com.example.ecommercebasic.dto.product.ProductRequestDto;
import com.example.ecommercebasic.dto.product.ProductSmallResponseDto;
import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return new ResponseEntity<>(productService.createProduct(productRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductSmallResponseDto>> getAllProductsByCategory(@RequestParam int categoryId) {
        return new ResponseEntity<>(productService.getAllProductsByCategory(categoryId),HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Product> getProductById(@RequestParam int id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/secure")
    public String secure() {
        return "secure";
    }


    @GetMapping("/not-secure")
    public String notsecure() {
        return "notsecure";
    }
}
