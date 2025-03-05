package com.example.ecommercebasic.service.product;

import com.example.ecommercebasic.builder.product.order.OrderBuilder;
import com.example.ecommercebasic.config.validation.RegexValidation;
import com.example.ecommercebasic.dto.product.order.BasketResponseDto;
import com.example.ecommercebasic.dto.product.order.OrderFilterRequest;
import com.example.ecommercebasic.dto.product.order.OrderRequestDto;
import com.example.ecommercebasic.dto.product.order.OrderResponseDto;
import com.example.ecommercebasic.entity.payment.Payment;
import com.example.ecommercebasic.entity.payment.PaymentStatus;
import com.example.ecommercebasic.entity.product.Category;
import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.entity.product.order.Order;
import com.example.ecommercebasic.entity.product.order.OrderItem;
import com.example.ecommercebasic.entity.product.order.OrderStatus;
import com.example.ecommercebasic.entity.user.User;
import com.example.ecommercebasic.exception.BadRequestException;
import com.example.ecommercebasic.repository.product.OrderRepository;
import com.example.ecommercebasic.service.user.CustomerService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final OrderItemService orderItemService;
    private final RegexValidation regexValidation;
    private final OrderBuilder orderBuilder;

    public OrderService(OrderRepository orderRepository, CustomerService customerService , OrderItemService orderItemService, RegexValidation regexValidation, OrderBuilder orderBuilder) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.orderItemService = orderItemService;
        this.regexValidation = regexValidation;
        this.orderBuilder = orderBuilder;
    }

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;

        if (authentication.isAuthenticated()){
            Object principal = authentication.getPrincipal();
            if (principal instanceof User userDetails){
                user = customerService.findByUsername(userDetails.getUsername());
            }else if (authentication instanceof AnonymousAuthenticationToken){
                System.out.println(authentication.getPrincipal());
                user = null;
            }else
                throw new BadRequestException("Invalid User");
        }

        List<OrderItem> orderItems = orderRequestDto.getOrderItems().stream().map(orderItemService::createOrderItem).toList();
        BigDecimal totalPrice = orderItemService.totalPrice(orderItems);

        String orderCode;
        while (orderRepository.existsByOrderCode(orderCode = regexValidation.generateUniqueOrderCode())) {}

        Order order = new Order(user,orderItems,totalPrice,orderCode);
        Order save = orderRepository.save(order);
        return orderBuilder.orderToOrderResponseDto(save);
    }

    public OrderStatus statusOrder(String orderCode,OrderStatus orderStatus) {
        Order order = findByOrderCode(orderCode);
        order.setStatus(orderStatus);
        return orderRepository.save(order).getStatus();
    }

    public List<OrderResponseDto> getAllOrders(int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        return orderRepository.findAll(pageable).stream().map(orderBuilder::orderToOrderResponseDto).collect(Collectors.toList());
    }



    public Order findByOrderCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode).orElseThrow(() -> new BadRequestException("Invalid Order Code"));
    }

    public List<BasketResponseDto> showBasket(List<Integer> productIds) {
        return productIds.stream().map(x-> {
            Product productById = orderItemService.findProductById(x);
            return new BasketResponseDto(productById.getId(),
                    productById.getProductName(),
                    productById.getCoverUrl(),
                    productById.getPrice(),
                    productById.getDiscountPrice());
        }).toList();
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order findByPayment(Payment payment) {
        return orderRepository.findByPayments(payment).orElseThrow(() -> new BadRequestException("Payment not found"));
    }

    public List<Order> filterOrdersByRequest(OrderFilterRequest filterRequest, int page, int size) {
        Sort sort = Sort.unsorted();
        if (filterRequest.getSortBy() != null) {
            sort = Sort.by(Sort.Direction.fromString(filterRequest.getSortDirection()), filterRequest.getSortBy());
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        PaymentStatus paymentStatus = PaymentStatus.valueOf(filterRequest.getPaymentStatus());
        Specification<Order> spec = filterOrders(paymentStatus);

        return orderRepository.findAll(spec, pageable).getContent();
    }


    public Specification<Order> filterOrders(PaymentStatus paymentStatus) {
        return Specification
                .where(hasPaymentStatus(paymentStatus));
    }


    public Specification<Order> hasPaymentStatus(PaymentStatus paymentStatus) {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (paymentStatus == null) return null;
            // Order ile Payment arasında INNER JOIN yap
            Join<Order, Payment> paymentJoin = root.join("payments", JoinType.INNER);

            return cb.equal(paymentJoin.get("status"), paymentStatus);
        };
    }



}
