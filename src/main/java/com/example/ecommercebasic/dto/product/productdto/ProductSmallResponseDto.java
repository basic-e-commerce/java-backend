package com.example.ecommercebasic.dto.product.productdto;

import java.math.BigDecimal;
import java.util.List;

public class ProductSmallResponseDto {
    private int id;
    private String name;
    private String coverImage;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String unitType;
    private int stock;
    private int soldQuantity;
    private List<String> categoryName;
    private double rating;
    private int commentQuantity;

    public ProductSmallResponseDto(int id, String name, String coverImage, BigDecimal price, BigDecimal discountPrice, String unitType, int stock, int soldQuantity, List<String> categoryName, double rating, int commentQuantity) {
        this.id = id;
        this.name = name;
        this.coverImage = coverImage;
        this.price = price;
        this.discountPrice = discountPrice;
        this.unitType = unitType;
        this.stock = stock;
        this.soldQuantity = soldQuantity;
        this.categoryName = categoryName;
        this.rating = rating;
        this.commentQuantity = commentQuantity;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public List<String> getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(List<String> categoryName) {
        this.categoryName = categoryName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCommentQuantity() {
        return commentQuantity;
    }

    public void setCommentQuantity(int commentQuantity) {
        this.commentQuantity = commentQuantity;
    }
}
