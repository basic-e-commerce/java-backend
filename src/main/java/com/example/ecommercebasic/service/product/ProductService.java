package com.example.ecommercebasic.service.product;

import com.example.ecommercebasic.builder.product.ProductBuilder;
import com.example.ecommercebasic.constant.ApplicationConstant;
import com.example.ecommercebasic.dto.product.ProductRequestDto;
import com.example.ecommercebasic.dto.product.ProductResponseDto;
import com.example.ecommercebasic.dto.product.ProductSmallResponseDto;
import com.example.ecommercebasic.entity.product.Category;
import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.exception.BadRequestException;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final CategoryService categoryService;
    private final FileService fileService;
    private final ProductBuilder productBuilder;
    private final ProductRepository productRepository;

    public ProductService(CategoryService categoryService, FileService fileService, ProductBuilder productBuilder, ProductRepository productRepository) {
        this.categoryService = categoryService;
        this.fileService = fileService;
        this.productBuilder = productBuilder;
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductRequestDto productRequestDto) {
        if (productRequestDto.getQuantity() <=0)
            throw new BadRequestException("Quantity must be greater than 0");

        if (productRequestDto.getPrice() <=0)
            throw new BadRequestException("Price must be greater than 0");

        if (productRepository.existsByProductNameEqualsIgnoreCase(productRequestDto.getProductName()))
            throw new BadRequestException("Product name already exists");

        Product product = productBuilder.productRequestDtoToProduct(productRequestDto);
        List<Category> categories = productRequestDto.getCategoryId().stream().map(categoryService::findById).toList();
        Set<Category> productCategories = new HashSet<>();

        for (int i = 0;i<categories.size();i++) {
            if (categories.get(i).isSubCategory()) {
                productCategories.add(categories.get(i));
            }else
                throw new BadRequestException("Category is not a sub category: "+categories.get(i).getName());
        }
        product.setCategories(productCategories);
        return productRepository.save(product);
    }

    public String addCategoryProduct(List<Integer> categoryId,int productId){
        Product product = findById(productId);

        List<Category> categories = categoryId.stream().map(categoryService::findById).toList();
        Set<Category> productCategories = new HashSet<>();

        for (int i = 0;i<categories.size();i++) {
            if (categories.get(i).isSubCategory()) {
                productCategories.add(categories.get(i));
            }else
                throw new BadRequestException("Category is not a sub category: "+categories.get(i).getName());
        }
        product.setCategories(productCategories);
        productRepository.save(product);
        return "Product added successfully";
    }

    public String removeProductCategory(List<Integer> categoryId,int productId){
        Product product = findById(productId);
        List<Category> categories = categoryId.stream().map(categoryService::findById).toList();
        for (int i = 0;i<categories.size();i++) {
            if (product.getCategories().contains(categories.get(i))) {
                product.getCategories().remove(categories.get(i));
            }else
                throw new BadRequestException("CAtegories do not exist");
        }
        productRepository.save(product);
        return "Product removed successfully";
    }




    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<ProductSmallResponseDto> getAllProductsByCategory(int categoryId) {
        Category category = categoryService.findById(categoryId);
        return productRepository
                .findAllByCategories(category)
                .stream()
                .map(productBuilder::productToProductSmallResponseDto)
                .collect(Collectors.toList());
    }

    public List<ProductSmallResponseDto> getAllProductsByCategoryAndTrue(int categoryId) {
        Category category = categoryService.findById(categoryId);
        return productRepository
                .findAllByCategoriesAndStatus(category,true)
                .stream()
                .map(productBuilder::productToProductSmallResponseDto)
                .collect(Collectors.toList());
    }
    public ProductResponseDto findByIdDto(int id){
        return productBuilder.productToProductResponseDto(findById(id));
    }

    public Product findById(int id) {
        return getById(id);
    }

    private Product getById(int id){
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException(ApplicationConstant.NOT_FOUND));
    }

    public Product findByName(String productName) {
        return productRepository.findByProductNameEqualsIgnoreCase(productName).orElseThrow(() -> new NotFoundException(ApplicationConstant.NOT_FOUND));
    }

    public List<ProductSmallResponseDto> getAllProductsByCategoryNameAndTrue(String categoryName) {
        Category category = categoryService.findByCategoryName(categoryName);
        System.out.println(category.getName());
        return productRepository
                .findAllByCategoriesAndStatus(category,true)
                .stream()
                .map(productBuilder::productToProductSmallResponseDto)
                .collect(Collectors.toList());
    }


    public String deleteProductById(int id) {
        Product product = findById(id);
        if (!product.getCategories().isEmpty()){
            for (Category category : product.getCategories()) {
                product.getCategories().remove(category);
            }
        }

        if (product.getCoverUrl()==null || product.getCoverUrl().isEmpty()){
            fileService.deleteImage(product.getCoverUrl());
        }
        if (!product.getImages().isEmpty()){
            for (int i = 0;i<product.getImages().size();i++) {
                fileService.deleteImage(product.getImages().get(i));
            }
        }
        productRepository.save(product);
        productRepository.delete(product);
        return "Product deleted successfully";
    }
}
