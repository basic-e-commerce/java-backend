package com.example.ecommercebasic.controller.product;

import com.example.ecommercebasic.dto.product.CategoryRequestDto;
import com.example.ecommercebasic.dto.product.CategorySmallDto;
import com.example.ecommercebasic.service.product.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        return new ResponseEntity<>(categoryService.createCategory(categoryRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/parent")
    public ResponseEntity<List<CategorySmallDto>> getParentCategory() {
        return new ResponseEntity<>(categoryService.findParentCategories() , HttpStatus.OK);
    }

    @GetMapping("/by-id")
    public ResponseEntity<CategorySmallDto> getCategoryById(@RequestParam Integer categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId),HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCategory(@RequestParam Integer categoryId) {
        return new ResponseEntity<>(categoryService.delete(categoryId),HttpStatus.OK);
    }
    @PutMapping("/cover-image")
    public ResponseEntity<String> updateCoverImage(@RequestParam("image") MultipartFile file, @RequestParam("id") int id) {
        return new ResponseEntity<>(categoryService.updateCoverImage(file,id),HttpStatus.CREATED);
    }
}
