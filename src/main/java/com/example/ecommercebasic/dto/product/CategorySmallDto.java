package com.example.ecommercebasic.dto.product;


import java.util.List;

public class CategorySmallDto {
    private int id;
    private String name;
    private String url;
    private List<CategorySmallDto> subCategories;

    public CategorySmallDto(int id, String name, String url, List<CategorySmallDto> subCategories) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.subCategories = subCategories;
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
}