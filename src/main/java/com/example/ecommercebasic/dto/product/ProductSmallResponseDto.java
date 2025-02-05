package com.example.ecommercebasic.dto.product;

public class ProductSmallResponseDto {
    private int id;
    private String name;
    private String coverImage;
    private float price;

    public ProductSmallResponseDto(int id, String name, String coverImage, float price) {
        this.id = id;
        this.name = name;
        this.coverImage = coverImage;
        this.price = price;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
