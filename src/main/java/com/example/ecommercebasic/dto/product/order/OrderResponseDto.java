package com.example.ecommercebasic.dto.product.order;

import java.util.List;

public class OrderResponseDto {
    private String orderCode;
    private float totalPrice;
    private List<OrderItemResponseDto> orderItemResponseDtos;

    public OrderResponseDto(String orderCode, float totalPrice, List<OrderItemResponseDto> orderItemResponseDtos) {
        this.orderCode = orderCode;
        this.totalPrice = totalPrice;
        this.orderItemResponseDtos = orderItemResponseDtos;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItemResponseDto> getOrderItemResponseDtos() {
        return orderItemResponseDtos;
    }

    public void setOrderItemResponseDtos(List<OrderItemResponseDto> orderItemResponseDtos) {
        this.orderItemResponseDtos = orderItemResponseDtos;
    }
}
