package com.example.ecommercebasic.dto.product.productdto;

import java.math.BigDecimal;

public class ProductSmallResponseDto {
    private int id;
    private String name;
    private String coverImage;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String unitType;

    public ProductSmallResponseDto(int id, String name, String coverImage, BigDecimal price, BigDecimal discountPrice, String unitType) {
        this.id = id;
        this.name = name;
        this.coverImage = coverImage;
        this.price = price;
        this.discountPrice = discountPrice;
        this.unitType = unitType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
}
