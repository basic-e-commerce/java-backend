package com.example.ecommercebasic.dto.product.order;

import java.math.BigDecimal;

public class BasketResponseDto {
    private int id;
    private String name;
    private String coverImage;
    private BigDecimal price;
    private BigDecimal discountPrice;

    public BasketResponseDto(int id, String name, String coverImage, BigDecimal price, BigDecimal discountPrice) {
        this.id = id;
        this.name = name;
        this.coverImage = coverImage;
        this.price = price;
        this.discountPrice = discountPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getcoverImage() {
        return coverImage;
    }

    public void setcoverImage(String coverImage) {
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
}
