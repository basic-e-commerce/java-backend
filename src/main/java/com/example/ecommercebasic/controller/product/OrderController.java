package com.example.ecommercebasic.controller.product;

import com.example.ecommercebasic.dto.product.order.BasketResponseDto;
import com.example.ecommercebasic.dto.product.order.OrderFilterRequest;
import com.example.ecommercebasic.dto.product.order.OrderRequestDto;
import com.example.ecommercebasic.dto.product.order.OrderResponseDto;
import com.example.ecommercebasic.dto.product.productdto.ProductFilterRequest;
import com.example.ecommercebasic.dto.product.productdto.ProductSmallResponseDto;
import com.example.ecommercebasic.entity.payment.Payment;
import com.example.ecommercebasic.entity.product.order.Order;
import com.example.ecommercebasic.service.product.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return new ResponseEntity<>(orderService.createOrder(orderRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(orderService.getAllOrders(page,size),HttpStatus.OK);
    }

    @GetMapping("/basket")
    public ResponseEntity<List<BasketResponseDto>> showBasket(@RequestParam List<Integer> productIds) {
        return new ResponseEntity<>(orderService.showBasket(productIds), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Order>> getOrderFilter(@RequestBody OrderFilterRequest filterRequest,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(orderService.filterOrdersByRequest(filterRequest,page,size),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/order-success")
    public ResponseEntity<List<ProductSmallResponseDto>> getOrderSuccess(@RequestBody ProductFilterRequest filterRequest,@RequestParam int page,@RequestParam int size) {
        return new ResponseEntity<>(orderService.getProductSmallResponseWithTotalSold(filterRequest,page,size),HttpStatus.OK);
    }

}
