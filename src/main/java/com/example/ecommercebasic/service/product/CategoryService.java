package com.example.ecommercebasic.service.product;

import com.example.ecommercebasic.builder.product.CategoryBuilder;
import com.example.ecommercebasic.dto.product.CategoryRequestDto;
import com.example.ecommercebasic.dto.product.CategorySmallDto;
import com.example.ecommercebasic.entity.product.Category;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.repository.product.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryBuilder categoryBuilder;

    public CategoryService(CategoryRepository categoryRepository, CategoryBuilder categoryBuilder) {
        this.categoryRepository = categoryRepository;
        this.categoryBuilder = categoryBuilder;
    }

    public String createCategory(CategoryRequestDto categoryRequestDto) {
        if (categoryRequestDto.getName() == null || categoryRequestDto.getName().isBlank()) {
            throw new IllegalArgumentException("Category name is required.");
        }

        categoryRepository
                .existsByNameEqualsIgnoreCase(categoryRequestDto.getName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category name."));

        Category category = new Category();
        if (categoryRequestDto.getParentCategoryId() != 0 ) {
            Category parentCategory = findById(categoryRequestDto.getParentCategoryId());
            category.setParentCategory(parentCategory);
        }

        category.setName(categoryRequestDto.getName());
        categoryRepository.save(category);

        return "success";
    }

    public Category findById(int id) {
        return categoryRepository.
                findById(id).orElseThrow(() -> new NotFoundException("Category Not Found"));
    }

    public List<CategorySmallDto> findParentCategories() {
        List<Category> allByParentCategoryIsNull = categoryRepository.findAllByParentCategoryIsNull();
        return allByParentCategoryIsNull.stream()
                .map(categoryBuilder::buildCategory)
                .collect(Collectors.toList());
    }

    public CategorySmallDto getCategoryById(Integer categoryId) {
        Category category = findById(categoryId);
        return categoryBuilder.buildCategory(category);
    }

    public String delete(Integer categoryId) {
        // Kategori var mı diye kontrol et
        Category category = findById(categoryId);

        // Alt kategorileri recursive olarak sil
        deleteCategoryRecursively(category);

        return "Category and its subcategories successfully deleted.";
    }

    private void deleteCategoryRecursively(Category category) {
        // Alt kategoriler var mı kontrol et
        if (category.getSubCategories() != null && !category.getSubCategories().isEmpty()) {
            for (Category subCategory : category.getSubCategories()) {
                // Alt kategorileri recursive olarak sil
                deleteCategoryRecursively(subCategory);
            }
        }
        /**
        // Kapak resmini sil
        if (category.getCoverImage() != null) {
            imageService.deleteImage(category.getCoverImage());
        }

        // Ürün ilişkilerini sıfırla
        category.setProducts(null);
        **/

        // Ana kategoriyi veritabanından sil
        categoryRepository.delete(category);
    }
}
