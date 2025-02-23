package com.example.ecommercebasic.dto.product;


import com.example.ecommercebasic.entity.product.attribute.Attribute;

import java.util.List;
import java.util.Set;

public class CategorySmallDto {
    private int id;
    private String name;
    private String url;
    private boolean isSubCategory;
    private List<CategorySmallDto> subCategories;
    private Set<Attribute> attributes;

    public CategorySmallDto(int id, String name, String url, boolean isSubCategory, List<CategorySmallDto> subCategories, Set<Attribute> attributes) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.isSubCategory = isSubCategory;
        this.subCategories = subCategories;
        this.attributes = attributes;
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

    public List<CategorySmallDto> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategorySmallDto> subCategories) {
        this.subCategories = subCategories;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSubCategory() {
        return isSubCategory;
    }

    public void setSubCategory(boolean subCategory) {
        isSubCategory = subCategory;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }
}