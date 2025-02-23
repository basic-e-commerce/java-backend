package com.example.ecommercebasic.dto.product.attribute;

import java.util.List;

public class ProductAttributeRequestDto {
    private int productId;
    private int attributeId;
    private List<Integer> attributeValueIds;

    public ProductAttributeRequestDto(int productId, int attributeId, List<Integer> attributeValueIds) {
        this.productId = productId;
        this.attributeId = attributeId;
        this.attributeValueIds = attributeValueIds;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public List<Integer> getAttributeValueIds() {
        return attributeValueIds;
    }


    public int getProductId() {
        return productId;
    }
}
