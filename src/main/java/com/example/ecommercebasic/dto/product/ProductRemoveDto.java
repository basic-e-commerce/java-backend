package com.example.ecommercebasic.dto.product;

import java.util.List;

public class ProductRemoveDto {
    private int id;
    private List<String> images;

    public ProductRemoveDto(int id, List<String> images) {
        this.id = id;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public List<String> getImages() {
        return images;
    }
}
