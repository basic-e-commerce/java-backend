package com.example.ecommercebasic.builder.product;

import com.example.ecommercebasic.config.validation.RegexValidation;
import com.example.ecommercebasic.dto.product.productdto.ProductModelRequestDto;
import com.example.ecommercebasic.dto.product.productdto.ProductRequestDto;
import com.example.ecommercebasic.dto.product.productdto.ProductResponseDto;
import com.example.ecommercebasic.dto.product.productdto.ProductSmallResponseDto;
import com.example.ecommercebasic.entity.file.CoverImage;
import com.example.ecommercebasic.entity.file.ProductImage;
import com.example.ecommercebasic.entity.product.Category;
import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.entity.product.UnitType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductBuilder {

    public Product productRequestDtoToProduct(ProductRequestDto productRequestDto) {
        return new Product(
                productRequestDto.getProductName(),
                RegexValidation.sanitize(productRequestDto.getDescription()),
                productRequestDto.getProductCode(),
                productRequestDto.getQuantity(),
                productRequestDto.getPrice(),
                productRequestDto.getDiscountPrice(),
                productRequestDto.isStatus(),
                UnitType.fromValue(productRequestDto.getUnitType())
        );
    }

    public ProductResponseDto productToProductResponseDto(Product product){
        return new ProductResponseDto(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getProductCode(),
                product.getQuantity(),
                product.isStatus(),
                product.getCategories().stream().map(Category::getId).collect(Collectors.toList()),
                product.getCoverImage().getUrl(),
                product.getPrice(),
                product.getUnitType().getValue(),
                product.getDiscountPrice(),
                product.getProductImages().stream().map(ProductImage::getUrl).collect(Collectors.toList()));
    }

    public ProductSmallResponseDto productToProductSmallResponseDto(Product product){
        String url = "";
        if (product.getCoverImage()!= null) {
            url = product.getCoverImage().getUrl();
        }

        return new ProductSmallResponseDto(
                product.getId(),
                product.getProductName(),
                url,
                product.getPrice(),
                product.getDiscountPrice(),
                product.getUnitType().getValue(),
                product.getQuantity(),
                0,
                product.getCategories().stream().map(Category::getName).toList(),
                4.5,
                5
        );
    }

    public ProductSmallResponseDto productToProductSmallResponseDto(Product product,int soldQuantiy){
        String url = "";
        if (product.getCoverImage()!= null) {
            url = product.getCoverImage().getUrl();
        }

        return new ProductSmallResponseDto(
                product.getId(),
                product.getProductName(),
                url,
                product.getPrice(),
                product.getDiscountPrice(),
                product.getUnitType().getValue(),
                product.getQuantity(),
                soldQuantiy,
                product.getCategories().stream().map(Category::getName).toList(),
                4.5,
                5
        );
    }

    public Product productModelRequestDtoToProduct(ProductModelRequestDto productModelRequestDto) {
        return new Product(
                productModelRequestDto.getProductName(),
                productModelRequestDto.getProductCode(),
                RegexValidation.sanitize(productModelRequestDto.getDescription()),
                productModelRequestDto.getQuantity(),
                productModelRequestDto.getPrice(),
                productModelRequestDto.getDiscountPrice(),
                productModelRequestDto.isStatus(),
                UnitType.fromValue(productModelRequestDto.getUnitType())
        );
    }
}
