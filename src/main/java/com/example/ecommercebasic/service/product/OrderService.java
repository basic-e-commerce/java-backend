package com.example.ecommercebasic.service.product;

import com.example.ecommercebasic.config.validation.RegexValidation;
import com.example.ecommercebasic.dto.product.order.OrderRequestDto;
import com.example.ecommercebasic.entity.product.order.Order;
import com.example.ecommercebasic.entity.product.order.OrderItem;
import com.example.ecommercebasic.entity.user.Customer;
import com.example.ecommercebasic.entity.user.Guest;
import com.example.ecommercebasic.entity.user.User;
import com.example.ecommercebasic.exception.BadRequestException;
import com.example.ecommercebasic.repository.product.OrderRepository;
import com.example.ecommercebasic.service.payment.PaymentService;
import com.example.ecommercebasic.service.user.CustomerService;
import com.example.ecommercebasic.service.user.GuestService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final RegexValidation regexValidation;

    public OrderService(OrderRepository orderRepository,CustomerService customerService, ProductService productService, OrderItemService orderItemService, RegexValidation regexValidation) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.productService = productService;
        this.orderItemService = orderItemService;
        this.regexValidation = regexValidation;
    }

    public String createOrder(OrderRequestDto orderRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;

        if (authentication.isAuthenticated()){
            Object principal = authentication.getPrincipal();
            if (principal instanceof User userDetails){
                user = customerService.findByUsername(userDetails.getUsername());
            }else
                throw new BadRequestException("Invalid User");
        }

        List<OrderItem> orderItems = orderRequestDto.getOrderItems().stream().map(orderItemService::createOrderItem).toList();
        float totalPrice = orderItemService.totalPrice(orderItems);

        String orderCode;
        while (orderRepository.existsByOrderCode(orderCode = regexValidation.generateUniqueOrderCode())) {
            // Döngü, benzersiz bir kod bulana kadar devam eder
        }

        Order order = new Order(user,orderItems,totalPrice,orderCode);
        return null;
    }



}
