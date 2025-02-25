package com.example.ecommercebasic.service.product;

import com.example.ecommercebasic.builder.product.CategoryBuilder;
import com.example.ecommercebasic.builder.product.ProductBuilder;
import com.example.ecommercebasic.dto.product.CategoryRequestDto;
import com.example.ecommercebasic.dto.product.CategorySmallDto;
import com.example.ecommercebasic.entity.product.Category;
import com.example.ecommercebasic.entity.product.ImageType;
import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.entity.product.attribute.Attribute;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.repository.product.CategoryRepository;
import com.example.ecommercebasic.repository.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryBuilder categoryBuilder;
    private final FileService fileService;
    private final ProductRepository productRepository;
    private final ProductBuilder productBuilder;

    public CategoryService(CategoryRepository categoryRepository, CategoryBuilder categoryBuilder, FileService fileService, ProductRepository productRepository, ProductBuilder productBuilder) {
        this.categoryRepository = categoryRepository;
        this.categoryBuilder = categoryBuilder;
        this.fileService = fileService;
        this.productRepository = productRepository;
        this.productBuilder = productBuilder;
    }



    @Transactional
    public String createCategory(CategoryRequestDto categoryRequestDto) {
        System.out.println(1);
        if (categoryRequestDto.getName() == null || categoryRequestDto.getName().isBlank()) {
            throw new IllegalArgumentException("Category name is required.");
        }

        categoryRepository
                .existsByNameEqualsIgnoreCase(categoryRequestDto.getName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category name."));

        Set<Product> products = new HashSet<>();


        Category category = new Category();
        if (categoryRequestDto.getParentCategoryId() != 0 ) {
            Category parentCategory = findById(categoryRequestDto.getParentCategoryId());
            products = productRepository.findAllByCategories(parentCategory);

            System.out.println(2);
            Set<Attribute> attributes = parentCategory.getAttributes();
            parentCategory.setSubCategory(false);
            parentCategory.setAttributes(new HashSet<>());
            parentCategory.setProducts(new HashSet<>());

            for (Product product : products) {
                product.getCategories().remove(parentCategory);
                productRepository.save(product);
            }
            categoryRepository.save(parentCategory);

            category.setProducts(products);
            category.setParentCategory(parentCategory);
            category.setAttributes(attributes);
        }

        category.setName(categoryRequestDto.getName());
        category.setSubCategory(true);
        System.out.println(5);
        Category save = categoryRepository.save(category);
        System.out.println(6);
        System.out.println("asd: "+products.size());

        for (Product product : products) {
            System.out.println(product.getProductName());
        }

        Set<Product> productCopy = new HashSet<>(products); // Yeni bir set oluştur

        for (Product product : productCopy) {
            System.out.println(product.getProductName());
            System.out.println(7);
            product.getCategories().add(save);
            System.out.println(8);
            productRepository.save(product);
            System.out.println(10);
        }

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

        if (category.getAttributes() != null ) {
            category.setAttributes(null);
        }

        if (category.getParentCategory() != null) {
            Category parentCategory = category.getParentCategory();
            parentCategory.setSubCategory(true);
            categoryRepository.save(parentCategory);
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

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Set<Category> getAllSubCategories(Category category) {
        Set<Category> allSubCategories = new HashSet<>();
        findSubCategories(category, allSubCategories);
        return allSubCategories;
    }

    private void findSubCategories(Category category, Set<Category> allSubCategories) {
        for (Category subCategory : category.getSubCategories()) {
            System.out.println("findSubCategories: "+subCategory.getId());
            allSubCategories.add(subCategory);
            findSubCategories(subCategory, allSubCategories);
        }
    }

    public List<CategorySmallDto> findSubCategory() {
        List<Category> categories = categoryRepository.findSubCategories();
        return categories.stream().map(categoryBuilder::buildCategory).toList();
    }
}
