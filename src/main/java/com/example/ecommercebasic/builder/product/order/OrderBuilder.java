package com.example.ecommercebasic.builder.product.order;

import com.example.ecommercebasic.dto.product.order.OrderItemResponseDto;
import com.example.ecommercebasic.dto.product.order.OrderResponseDto;
import com.example.ecommercebasic.entity.product.order.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderBuilder {

    public OrderResponseDto orderToOrderResponseDto(Order order) {
        return new OrderResponseDto(
                order.getOrderCode(),
                order.getTotalPrice(),
                order.getOrderItems().stream().map(x-> {
                    return new OrderItemResponseDto(x.getProduct().getId(),x.getProduct().getProductName(),x.getQuantity());
                }).toList()
        );
    }
}
