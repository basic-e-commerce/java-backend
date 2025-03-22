package com.example.ecommercebasic.entity.file;

import com.example.ecommercebasic.entity.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class CoverImage extends Image {

    private String url;
    @JsonIgnore
    @OneToOne(mappedBy = "coverImage")
    private Product product;

    public CoverImage(String name, Long size, String resolution, String format, String url) {
        super(name, size, resolution, format);
        this.url = url;
    }

    public CoverImage() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
