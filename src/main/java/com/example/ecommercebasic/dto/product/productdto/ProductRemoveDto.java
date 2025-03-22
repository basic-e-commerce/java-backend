package com.example.ecommercebasic.dto.product.productdto;

import java.util.List;

public class ProductRemoveDto {
    private int id;
    private List<Long> productImageId;

    public ProductRemoveDto(int id, List<Long> productImageId) {
        this.id = id;
        this.productImageId = productImageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Long> getProductImageId() {
        return productImageId;
    }

    public void setProductImageId(List<Long> productImageId) {
        this.productImageId = productImageId;
    }
}
