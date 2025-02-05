package com.example.ecommercebasic.builder.product;

import com.example.ecommercebasic.dto.product.ProductRequestDto;
import com.example.ecommercebasic.dto.product.ProductResponseDto;
import com.example.ecommercebasic.dto.product.ProductSmallResponseDto;
import com.example.ecommercebasic.entity.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductBuilder {

    public Product productRequestDtoToProduct(ProductRequestDto productRequestDto) {
        return new Product(
                productRequestDto.getProductName(),
                productRequestDto.getDescription(),
                productRequestDto.getQuantity(),
                productRequestDto.getPrice(),
                productRequestDto.isStatus()
        );
    }

    public ProductResponseDto productToProductResponseDto(Product product){
        return new ProductResponseDto(product.getId(),
                product.getProductName(),
                product.getDescription(),
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
                product.getPrice()
        );
    }

}
