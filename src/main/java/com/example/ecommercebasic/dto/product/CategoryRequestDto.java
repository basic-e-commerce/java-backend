package com.example.ecommercebasic.dto.product;

public class CategoryRequestDto {
    private String name;
    private int parentCategoryId;

    public CategoryRequestDto(String name, int parentCategoryId) {
        this.name = name;
        this.parentCategoryId = parentCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
