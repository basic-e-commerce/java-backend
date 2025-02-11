package com.example.ecommercebasic.service.product;

import com.example.ecommercebasic.dto.product.order.OrderItemRequestDto;
import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.entity.product.order.OrderItem;
import com.example.ecommercebasic.repository.product.OrderItemRepository;
import com.example.ecommercebasic.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;

    public OrderItemService(OrderItemRepository orderItemRepository, ProductService productService) {
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
    }

    public OrderItem createOrderItem(OrderItemRequestDto orderItemRequestDto) {
        Product product = productService.findById(orderItemRequestDto.getProductId());
        OrderItem orderItem = new OrderItem(null,product, product.getQuantity(), product.getPrice());
        return orderItemRepository.save(orderItem);
    }


    public float totalPrice(List<OrderItem> orderItems) {
        float totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getPrice()*orderItem.getQuantity();
        }
        return totalPrice;
    }
}
