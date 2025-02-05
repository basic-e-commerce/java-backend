package com.example.ecommercebasic.dto.product;

import java.util.ArrayList;
import java.util.List;

public class ProductResponseDto {
    private int id;
    private String name;
    private String description;
    private List<String> images = new ArrayList<>();

    public ProductResponseDto(int id, String name, String description, List<String> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.images = images;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
