package com.example.ecommercebasic.dto.product.order;

import java.util.List;

public class OrderRequestDto {
    List<OrderItemRequestDto> orderItems;

    public OrderRequestDto(List<OrderItemRequestDto> orderItems) {
        this.orderItems = orderItems;
    }
    public List<OrderItemRequestDto> getOrderItems() {
        return orderItems;
    }
}
