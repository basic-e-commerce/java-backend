package com.example.ecommercebasic.entity.file;

import jakarta.persistence.Entity;


@Entity
public class ProductImage extends Image{
    private String url;
    private int orderIndex;

    public ProductImage(String name, Long size, String resolution, String format, String url , int orderIndex) {
        super(name, size, resolution, format);

        this.url = url;
        this.orderIndex = orderIndex;
    }

    public ProductImage() {

    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
}
