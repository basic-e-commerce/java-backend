package com.example.ecommercebasic.entity.file;

public enum ImageType {
    PRODUCT_COVER_IMAGE("product_cover_image"),
    PRODUCT_IMAGE("product_image"),
    CATEGORY_IMAGE("category_image");
    private String value;
    private ImageType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
