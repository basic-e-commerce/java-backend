package com.example.ecommercebasic.dto.product.order;

public class BasketResponseDto {
    private int id;
    private String name;
    private String coverImage;
    private float price;
    private float discountPrice;

    public BasketResponseDto(int id, String name, String coverImage, float price, float discountPrice) {
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
}
