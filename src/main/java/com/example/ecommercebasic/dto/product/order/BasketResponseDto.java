package com.example.ecommercebasic.dto.product.order;

public class BasketResponseDto {
    private int id;
    private String name;
    private String coverImage;
    private double price;
    private double discountPrice;

    public BasketResponseDto(int id, String name, String coverImage, double price, double discountPrice) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }
}
