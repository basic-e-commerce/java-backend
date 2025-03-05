package com.example.ecommercebasic.service.product;

import com.example.ecommercebasic.dto.product.order.OrderItemRequestDto;
import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.entity.product.order.OrderItem;
import com.example.ecommercebasic.repository.product.OrderItemRepository;
import com.example.ecommercebasic.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        OrderItem orderItem = new OrderItem(product, orderItemRequestDto.getProductQuantity(), product.getDiscountPrice());
        return orderItemRepository.save(orderItem);
    }


    public BigDecimal totalPrice(List<OrderItem> orderItems) {
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (OrderItem orderItem : orderItems) {
            // getcurrentPrice() ve getQuantity() BigDecimal türüne dönüştürülür
            BigDecimal itemPrice = orderItem.getcurrentPrice();
            BigDecimal quantity = BigDecimal.valueOf(orderItem.getQuantity());

            // Çarpma ve ekleme işlemleri BigDecimal metodları ile yapılır
            totalPrice = totalPrice.add(itemPrice.multiply(quantity));
        }
        return totalPrice;
    }

    public Product findProductById(int id) {
        return productService.findById(id);
    }
}
