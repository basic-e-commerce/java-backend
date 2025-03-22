package com.example.ecommercebasic.service.product;

import com.example.ecommercebasic.builder.product.ProductBuilder;
import com.example.ecommercebasic.constant.ApplicationConstant;
import com.example.ecommercebasic.dto.file.CoverImageRequestDto;
import com.example.ecommercebasic.dto.file.ImageRequestDto;
import com.example.ecommercebasic.dto.file.ProductImageRequestDto;
import com.example.ecommercebasic.dto.product.attribute.ProductFilterRequest;
import com.example.ecommercebasic.dto.product.productdto.*;
import com.example.ecommercebasic.entity.file.CoverImage;
import com.example.ecommercebasic.entity.file.ImageType;
import com.example.ecommercebasic.entity.file.ProductImage;
import com.example.ecommercebasic.entity.product.*;
import com.example.ecommercebasic.exception.BadRequestException;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.repository.product.ProductRepository;
import com.example.ecommercebasic.service.file.CoverImageService;
import com.example.ecommercebasic.service.file.ImageService;
import com.example.ecommercebasic.service.file.ProductImageService;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final CategoryService categoryService;
    private final ProductBuilder productBuilder;
    private final ProductRepository productRepository;
    private final CoverImageService coverImageService;
    private final ProductImageService productImageService;


    public ProductService(CategoryService categoryService , ProductBuilder productBuilder, ProductRepository productRepository, CoverImageService coverImageService, ProductImageService productImageService) {
        this.categoryService = categoryService;
        this.productBuilder = productBuilder;
        this.productRepository = productRepository;
        this.coverImageService = coverImageService;
        this.productImageService = productImageService;
    }

    public Product createProduct(ProductRequestDto productRequestDto) {
        if (productRequestDto.getPrice().compareTo(productRequestDto.getDiscountPrice()) < 0) {
            throw new BadRequestException("Price must be greater than discount price");
        }

        if (productRequestDto.getQuantity() <=0)
            throw new BadRequestException("Quantity must be greater than 0");

        if (productRequestDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Price must be greater than 0");
        }

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

    @Transactional
    public ProductResponseDto createBasicProductModel(ProductModelRequestDto productModelRequestDto) {
        if (productModelRequestDto.getPrice().compareTo(productModelRequestDto.getDiscountPrice()) < 0) {
            throw new BadRequestException("Price must be greater than discount price");
        }

        if (productModelRequestDto.getQuantity() <=0)
            throw new BadRequestException("Quantity must be greater than 0");

        if (productModelRequestDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Price must be greater than 0");
        }

        if (productRepository.existsByProductNameEqualsIgnoreCase(productModelRequestDto.getProductName()))
            throw new BadRequestException("Product name already exists");

        Product product = productBuilder.productModelRequestDtoToProduct(productModelRequestDto);

        List<Category> categories = productModelRequestDto.getCategoryId().stream().map(categoryService::findById).toList();
        Set<Category> productCategories = new HashSet<>();

        for (int i = 0;i<categories.size();i++) {
            if (categories.get(i).isSubCategory()) {
                productCategories.add(categories.get(i));
            }else
                throw new BadRequestException("Category is not a sub category: "+categories.get(i).getName());
        }
        product.setCategories(productCategories);
        Product saveProduct = productRepository.save(product);

        CoverImageRequestDto coverImageRequestDto = new CoverImageRequestDto(productModelRequestDto.getCoverImage());
        CoverImage coverImage = coverImageService.save(coverImageRequestDto, (long) product.getId());

        List<ProductImage> productImages = new ArrayList<>();

        for (int i = 0; i < productModelRequestDto.getImages().length; i++) {
            MultipartFile imageFile = productModelRequestDto.getImages()[i];

            // ProductImageRequestDto oluştur
            ProductImageRequestDto productImageRequestDto = new ProductImageRequestDto(imageFile, i);

            // Resmi kaydet
            ProductImage productImage = productImageService.save(productImageRequestDto, (long) product.getId());
            productImages.add(productImage);
        }

        product.setCoverImage(coverImage);
        product.setProductImages(productImages);
        return productBuilder.productToProductResponseDto(productRepository.save(saveProduct));
    }

    public ProductResponseDto createVariantProductModel(ProductVariantModelRequestDto productModelRequestDto) {
        return null;
    }


    public String addCategoryProduct(List<Integer> categoryId,int productId){
        Product product = findById(productId);

        List<Category> categories = categoryId.stream().map(categoryService::findById).toList();
        Set<Category> productCategories = product.getCategories();

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
                throw new BadRequestException("Categories do not exist");
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

    public Set<ProductSmallResponseDto> getAllProductsByCategoryAndTrue(int categoryId) {
        Category category = categoryService.findById(categoryId);
        Set<ProductSmallResponseDto> products = new HashSet<>();
        if (!category.isSubCategory()){
            Set<Category> categories = categoryService.getLeafCategories(category);
            for (Category subCategory : categories){
                System.out.println("subCategory: " + subCategory.getName());
                products.addAll(productRepository
                        .findAllByCategoriesAndStatus(subCategory,true)
                        .stream()
                        .map(productBuilder::productToProductSmallResponseDto)
                        .collect(Collectors.toSet()));
            }
        }else{
            products = productRepository
                    .findAllByCategoriesAndStatus(category,true)
                    .stream()
                    .map(productBuilder::productToProductSmallResponseDto)
                    .collect(Collectors.toSet());
        }

        return products;
    }
    public ProductResponseDto findByIdDto(int id){
        Product product = findById(id);

        return productBuilder.productToProductResponseDto(product);
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

        if (!product.getCategories().isEmpty()) {
            Iterator<Category> iterator = product.getCategories().iterator();
            while (iterator.hasNext()) {
                iterator.next();
                iterator.remove(); // güvenli bir şekilde öğeyi siler
            }
        }

        if (product.getCoverImage() != null) {
            System.out.println("cover image");
            coverImageService.delete(product.getCoverImage().getId());
        }

        if (!product.getProductImages().isEmpty()) {
            for (int i = 0; i < product.getProductImages().size(); i++) {
                System.out.println("product image " + product.getProductImages().get(i).getName());
                productImageService.delete(product.getProductImages().get(i).getId());
            }
        }

        productRepository.save(product);
        productRepository.delete(product);

        return "Product deleted successfully";
    }


    @Transactional
    public String updateProductCoverImage(MultipartFile file, int id) {
        Product product = findById(id);

        if (product.getCoverImage() != null){
            String s = coverImageService.delete(product.getCoverImage().getId());
            if(s.equals("deleted")){
                System.out.println("deleted product cover image"+product.getId());
                product.setCoverImage(null);
                coverImageService.delete(product.getCoverImage().getId());
                productRepository.save(product);
            }
        }
        ImageRequestDto imageRequestDto = new ImageRequestDto(file);
        CoverImage save = coverImageService.save(imageRequestDto, (long) product.getId());
        product.setCoverImage(save);
        productRepository.save(product);
        return "Product cover image updated successfully";
    }


    @Transactional
    public String updateProductImage(MultipartFile[] files, int id) {
        Product product = findById(id);
        ArrayList<ProductImage> images = new ArrayList<>();

        for (MultipartFile file : files) {
            ProductImageRequestDto imageRequestDto = new ProductImageRequestDto(file,0);
            ProductImage save = productImageService.save(imageRequestDto, (long) product.getId());
            images.add(save);
        }

        List<ProductImage> currentImages = product.getProductImages();
        currentImages.addAll(images);
        product.setProductImages(currentImages);
        productRepository.save(product);
        return "Product image updated successfully";
    }

    @Transactional
    public List<String> updateProductImageWithProduct(MultipartFile[] files, Product product) {
        ArrayList<ProductImage> images = new ArrayList<>();

        for (MultipartFile file : files) {
            ProductImageRequestDto imageRequestDto = new ProductImageRequestDto(file,0);
            ProductImage save = productImageService.save(imageRequestDto, (long) product.getId());
            images.add(save);
        }
        return images.stream().map(ProductImage::getUrl).collect(Collectors.toList());
    }


    @Transactional
    public String removeProductImage(ProductRemoveDto productRemoveDto) {
        Product product = findById(productRemoveDto.getId());
        for (Long imageId : productRemoveDto.getProductImageId()) {
            ProductImage productImage = productImageService.getById(imageId);
            if (product.getProductImages().contains(productImage)) {
                productImageService.delete(productImage.getId());
                product.getProductImages().remove(productImage);
            }else
                throw new BadRequestException(ApplicationConstant.BAD_REQUEST +":"+ imageId);
        }
        productRepository.save(product);
        return "Product image removed successfully";
    }

    public List<ProductSmallResponseDto> getAllProductSmall() {
        return productRepository.findAll().stream().map(productBuilder::productToProductSmallResponseDto).collect(Collectors.toList());
    }

    public ProductResponseDto updateProduct(Integer productId, ProductRequestDto productRequestDto) {

        if (productRequestDto.getPrice().compareTo(productRequestDto.getDiscountPrice()) < 0) {
            throw new BadRequestException("Price must be greater than discount price");
        }

        Product product = findById(productId);
        product.setProductName(productRequestDto.getProductName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setDiscountPrice(productRequestDto.getDiscountPrice());
        switch (productRequestDto.getUnitType()){
            case "kg":
                product.setUnitType(UnitType.KILOGRAM);
                break;
            case "g":
                product.setUnitType(UnitType.GRAM);
                break;
            case "L":
                product.setUnitType(UnitType.LITER);
                break;
            case "ml":
                product.setUnitType(UnitType.MILLILITER);
                break;
            case "unit":
                product.setUnitType(UnitType.UNIT);
                break;
            default:
                throw new BadRequestException(ApplicationConstant.BAD_REQUEST +":"+ productRequestDto.getUnitType());
        }
        product.setStatus(productRequestDto.isStatus());
        Product save = productRepository.save(product);
        return productBuilder.productToProductResponseDto(save);
    }

    public List<ProductSmallResponseDto> filterProductsByCategory(ProductFilterRequest filterRequest,int page,int size) {
        Sort sort = Sort.unsorted();
        if (filterRequest.getSortBy() != null) {
            sort = Sort.by(Sort.Direction.fromString(filterRequest.getSortDirection()), filterRequest.getSortBy());
        }
        Category category = categoryService.findById(filterRequest.getCategoryId());
        Set<Integer> subCategories = categoryService.getLeafCategories(category).stream().map(Category::getId).collect(Collectors.toSet());
        for (Integer inte: subCategories){
            System.out.println(inte);
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Product> spec = filterProducts(subCategories,filterRequest.getMinPrice(),filterRequest.getMaxPrice(),true);
        return productRepository.findAll(spec,pageable).stream().map(productBuilder::productToProductSmallResponseDto).collect(Collectors.toList());
    }

    public Specification<Product> filterProducts(Set<Integer> categoriesId, Double minPrice, Double maxPrice,boolean isAvailable) {
        return Specification
                .where(hasCategories(categoriesId))
                .and(hasMinPrice(minPrice))
                .and(hasMaxPrice(maxPrice))
                .and(isAvailable(isAvailable))
                .and(isDeleted(false));
    }

    public Specification<Product> hasCategories(Set<Integer> categoryIds) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (categoryIds == null || categoryIds.isEmpty()) return null;
            Join<Product, Category> categoryJoin = root.join("categories", JoinType.INNER);
            return categoryJoin.get("id").in(categoryIds);
        };
    }


    public Specification<Product> hasProductType(ProductType productType) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                productType == null ? null : cb.equal(root.get("productType"), productType);
    }

    public Specification<Product> hasMinPrice(Double minPrice) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("discountPrice"), minPrice);
    }

    public Specification<Product> hasMaxPrice(Double maxPrice) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("discountPrice"), maxPrice);
    }

    public Specification<Product> isAvailable(boolean status) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("status"), status);
    }

    public Specification<Product> isDeleted(boolean isDeleted) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("isDeleted"), isDeleted);
    }


    public Page<Product> getAllProductsByFilter(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public ProductSmallResponseDto getProductSmallResponseDto(Product product,int quantity) {
        return productBuilder.productToProductSmallResponseDto(product,quantity);
    }
}
