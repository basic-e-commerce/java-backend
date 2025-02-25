package com.example.ecommercebasic.dto.product.productdto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductVariantModelRequestDto {

    private String productName;
    private String description;
    private String productCode;
    private int quantity;
    private double price;
    private double discountPrice;
    private String unitType;
    private boolean status;
    private List<Integer> categoryId;
    private MultipartFile[] images;
    private MultipartFile coverImage;
    private int attributeId;
    private int attributeValueId;

    public ProductVariantModelRequestDto(String productName, String description, String productCode, int quantity, double price, double discountPrice, String unitType, boolean status, List<Integer> categoryId, MultipartFile[] images, MultipartFile coverImage, int attributeId, int attributeValueId) {
        this.productName = productName;
        this.description = description;
        this.productCode = productCode;
        this.quantity = quantity;
        this.price = price;
        this.discountPrice = discountPrice;
        this.unitType = unitType;
        this.status = status;
        this.categoryId = categoryId;
        this.images = images;
        this.coverImage = coverImage;
        this.attributeId = attributeId;
        this.attributeValueId = attributeValueId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public String getUnitType() {
        return unitType;
    }

    public boolean isStatus() {
        return status;
    }

    public List<Integer> getCategoryId() {
        return categoryId;
    }

    public MultipartFile[] getImages() {
        return images;
    }

    public MultipartFile getCoverImage() {
        return coverImage;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public int getAttributeValueId() {
        return attributeValueId;
    }
}
