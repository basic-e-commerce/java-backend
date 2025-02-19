package com.example.ecommercebasic.dto.product.productdto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductModelRequestDto {
    private String productName;
    private String description;
    private int quantity;
    private double price;
    private double discountPrice;
    private String unitType;
    private boolean status;
    private List<Integer> categoryId;
    private MultipartFile[] images;
    private MultipartFile coverImage;

    public ProductModelRequestDto(String productName, String description, int quantity, double price, double discountPrice, String unitType, boolean status, List<Integer> categoryId, MultipartFile[] images, MultipartFile coverImage) {
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.discountPrice = discountPrice;
        this.unitType = unitType;
        this.status = status;
        this.categoryId = categoryId;
        this.images = images;
        this.coverImage = coverImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Integer> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(List<Integer> categoryId) {
        this.categoryId = categoryId;
    }

    public MultipartFile[] getImages() {
        return images;
    }

    public void setImages(MultipartFile[] images) {
        this.images = images;
    }

    public MultipartFile getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(MultipartFile coverImage) {
        this.coverImage = coverImage;
    }
}
