package com.example.ecommercebasic.dto.product.order;

public class OrderItemRequestDto {
    private int productId;
    private int productQuantity;

    public OrderItemRequestDto(int productId, int productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }
}
