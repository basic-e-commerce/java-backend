package com.example.ecommercebasic.dto.product;

import java.util.List;

public class ProductRequestDto {
    private String productName;
    private String description;
    private int quantity;
    private float price;
    private float discountPrice;
    private String unitType;
    private boolean status;

    private List<Integer> categoryId;

    public ProductRequestDto(String productName, String description, int quantity, float price, float discountPrice, String unitType, boolean status, List<Integer> categoryId) {
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.discountPrice = discountPrice;
        this.unitType = unitType;
        this.status = status;
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public boolean isStatus() {
        return status;
    }

    public List<Integer> getCategoryId() {
        return categoryId;
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
