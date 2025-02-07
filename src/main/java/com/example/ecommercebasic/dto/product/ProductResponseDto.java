package com.example.ecommercebasic.dto.product;

import java.util.List;

public class ProductResponseDto {
    private int id;
    private String name;
    private String description;
    private String coverImage;
    private float price;
    private String unitType;
    private float discountPrice;
    private List<String> images;

    public ProductResponseDto(int id, String name, String description, String coverImage, float price, String unitType, float discountPrice, List<String> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coverImage = coverImage;
        this.price = price;
        this.unitType = unitType;
        this.discountPrice = discountPrice;
        this.images = images;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
}
