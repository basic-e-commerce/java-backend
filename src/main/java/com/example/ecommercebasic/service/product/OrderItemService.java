package com.example.ecommercebasic.service.product;

import com.example.ecommercebasic.dto.product.order.OrderItemRequestDto;
import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.entity.product.order.Order;
import com.example.ecommercebasic.entity.product.order.OrderItem;
import com.example.ecommercebasic.entity.product.order.OrderStatus;
import com.example.ecommercebasic.repository.product.OrderItemRepository;
import com.example.ecommercebasic.repository.product.ProductRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;


    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public static Specification<OrderItem> hasProductAndSuccessfulOrder(Product product) {
        return (root, query, criteriaBuilder) -> {
            // Join Order ile OrderItem
            var orderJoin = root.join("order");

            // Order'ın SUCCESS durumunda olması
            var orderStatus = criteriaBuilder.equal(orderJoin.get("status"), OrderStatus.CONFIRMED);

            // Product ile ilişkilendirilen OrderItem'ları getirmek
            var productCondition = criteriaBuilder.equal(root.get("product"), product);

            return criteriaBuilder.and(orderStatus, productCondition);
        };
    }

    public List<OrderItem> findSuccessfulOrderItemsWithProduct(Product product) {
        // OrderItem Specification ile sorgu yapma
        Specification<OrderItem> specification = hasProductAndSuccessfulOrder(product);

        // Sorguyu çalıştırarak sonuçları döndürme
        return orderItemRepository.findAll(specification);
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

    List<OrderItem> findAllByProduct(Product product) {
        return orderItemRepository.findAllByProduct(product);
    }

}
