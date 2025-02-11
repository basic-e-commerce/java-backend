package com.example.ecommercebasic.builder.product;

import com.example.ecommercebasic.config.validation.RegexValidation;
import com.example.ecommercebasic.dto.product.ProductRequestDto;
import com.example.ecommercebasic.dto.product.ProductResponseDto;
import com.example.ecommercebasic.dto.product.ProductSmallResponseDto;
import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.entity.product.UnitType;
import org.springframework.stereotype.Component;

@Component
public class ProductBuilder {

    public Product productRequestDtoToProduct(ProductRequestDto productRequestDto) {
        return new Product(
                productRequestDto.getProductName(),
                RegexValidation.sanitize(productRequestDto.getDescription()),
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
                product.getCoverUrl(),
                product.getPrice(),
                product.getUnitType().getValue(),
                product.getDiscountPrice(),
                product.getImages());
    }

    public ProductSmallResponseDto productToProductSmallResponseDto(Product product){
        String url = "";
        if (product.getCoverUrl()!= null) {
            url = product.getCoverUrl();
        }

        return new ProductSmallResponseDto(
                product.getId(),
                product.getProductName(),
                url,
                product.getPrice(),
                product.getDiscountPrice(),
                product.getUnitType().getValue()
        );
    }

}
