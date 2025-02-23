package com.example.ecommercebasic.controller.product;

import com.example.ecommercebasic.dto.product.productdto.*;
import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.entity.product.attribute.AttributeValue;
import com.example.ecommercebasic.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping("/model")
    public ResponseEntity<ProductResponseDto> createBasicProductModel(@ModelAttribute ProductModelRequestDto productModelRequestDto) {
        return new ResponseEntity<>(productService.createBasicProductModel(productModelRequestDto),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductRequestDto productRequestDto) {

    }
    

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/small")
    public ResponseEntity<List<ProductSmallResponseDto>> getAllProductSmall() {
        return new ResponseEntity<>(productService.getAllProductSmall(), HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductSmallResponseDto>> getAllProductsByCategoryAndTrue(@RequestParam int categoryId) {
        return new ResponseEntity<>(productService.getAllProductsByCategoryAndTrue(categoryId),HttpStatus.OK);
    }
    @GetMapping("/category/by-name")
    public ResponseEntity<List<ProductSmallResponseDto>> getAllProductsByCategoryNameAndTrue(@RequestParam String categoryName) {
        return new ResponseEntity<>(productService.getAllProductsByCategoryNameAndTrue(categoryName),HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Product> getProductById(@RequestParam int id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<Product> getProductByName(@RequestParam String productName) {
        return new ResponseEntity<>(productService.findByName(productName), HttpStatus.OK);
    }

    @GetMapping("/by-id")
    public ResponseEntity<ProductResponseDto> getProductResponseById(@RequestParam int id) {
        return new ResponseEntity<>(productService.findByIdDto(id), HttpStatus.OK);
    }

    @PutMapping("/add-category")
    public ResponseEntity<String> addCategoryProduct(@RequestParam List<Integer> categoriesId,@RequestParam Integer productId) {
        return new ResponseEntity<>(productService.addCategoryProduct(categoriesId, productId), HttpStatus.OK);
    }
    @PutMapping("/add-attribute")
    public ResponseEntity<String> addAttributeProduct(@RequestParam Integer productId, @RequestParam Integer attributeId, @RequestParam List<Integer> attributeValueIds ) {
        return new ResponseEntity<>(productService.addAttribute(productId, attributeId,attributeValueIds), HttpStatus.OK);
    }

    @PutMapping("/remove-category")
    public ResponseEntity<String> removeCategoryProduct(@RequestParam List<Integer> categoriesId,@RequestParam Integer productId) {
        return new ResponseEntity<>(productService.removeProductCategory(categoriesId, productId), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductById(@RequestParam int id) {
        return new ResponseEntity<>(productService.deleteProductById(id),HttpStatus.OK);
    }

    @PutMapping("/cover-image")
    public ResponseEntity<String> updateProductCoverImage(@RequestParam("image") MultipartFile file, @RequestParam("id") int id) {
        return new ResponseEntity<>(productService.updateProductCoverImage(file,id),HttpStatus.CREATED);
    }

    @PutMapping("/product-image")
    public ResponseEntity<String> updateProductImage(@RequestParam("images") MultipartFile[] files, @RequestParam("id") int id) {
        return new ResponseEntity<>(productService.updateProductImage(files,id),HttpStatus.CREATED);
    }

    @DeleteMapping("/remove-image")
    public ResponseEntity<String> removeProductImage(@RequestBody ProductRemoveDto productRemoveDto) {
        return new ResponseEntity<>(productService.removeProductImage(productRemoveDto),HttpStatus.OK);
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
