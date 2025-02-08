package com.example.ecommercebasic.service.product;

import com.example.ecommercebasic.builder.product.CategoryBuilder;
import com.example.ecommercebasic.dto.product.CategoryRequestDto;
import com.example.ecommercebasic.dto.product.CategorySmallDto;
import com.example.ecommercebasic.entity.product.Category;
import com.example.ecommercebasic.entity.product.ImageType;
import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.repository.product.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryBuilder categoryBuilder;
    private final FileService fileService;

    public CategoryService(CategoryRepository categoryRepository, CategoryBuilder categoryBuilder, FileService fileService) {
        this.categoryRepository = categoryRepository;
        this.categoryBuilder = categoryBuilder;
        this.fileService = fileService;
    }

    @Transactional
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
            parentCategory.setSubCategory(false);
            for (Product product : parentCategory.getProducts()) {
                System.out.println(product.getProductName());
            }
            category.setProducts(parentCategory.getProducts());
            parentCategory.setProducts(null);
            category.setParentCategory(parentCategory);
            categoryRepository.save(parentCategory);
        }

        category.setName(categoryRequestDto.getName());
        category.setSubCategory(true);
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
                deleteCategoryRecursively(subCategory);
            }
        }

        if (category.getCoverUrl() != null){
            fileService.deleteImage(category.getCoverUrl());
        }

        if (category.getProducts() != null ) {
            System.out.println("getProduct");
            categoryRepository.deleteCategoryProductRelations(category.getId());
            category.setProducts(null);
            categoryRepository.save(category);
        }

        categoryRepository.delete(category);
    }

    @Transactional
    public String updateCoverImage(MultipartFile file, int id) {
        Category category = findById(id);

        if (category.getCoverUrl() != null) {
            String s = fileService.deleteImage(category.getCoverUrl());
            if (s.equals("deleted")){
                System.out.println("deleted category image id: "+category.getId());
                category.setCoverUrl(null);
                categoryRepository.save(category);
            }
        }

        String path = ImageType.CATEGORY_IMAGE.getValue()+"/"+category.getId()+"/";
        String newPath = fileService.uploadImage(file, path);
        category.setCoverUrl(newPath);
        categoryRepository.save(category);
        return "success";
    }

    public Category findByCategoryName(String categoryName) {
        return categoryRepository.findByNameEqualsIgnoreCase(categoryName).orElseThrow(() -> new IllegalArgumentException("Invalid category name."));
    }
}
