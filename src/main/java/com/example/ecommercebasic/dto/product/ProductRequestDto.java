package com.example.ecommercebasic.dto.product;

import java.util.List;

public class ProductRequestDto {
    private String productName;
    private String description;
    private int quantity;
    private float price;
    private boolean status;

    private List<Integer> categoryId;

    public ProductRequestDto(String productName, String description, int quantity, float price, boolean status, List<Integer> categoryId) {
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
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
}
