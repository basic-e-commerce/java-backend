package com.example.ecommercebasic.service.payment;

import com.example.ecommercebasic.entity.product.order.Order;
import com.example.ecommercebasic.entity.product.order.OrderStatus;
import com.example.ecommercebasic.exception.BadRequestException;
import com.example.ecommercebasic.service.product.OrderService;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final OrderService orderService;

    public PaymentService(OrderService orderService) {
        this.orderService = orderService;
    }

    public String processPayment(String paymentMethod,String orderCode,float price) {
        Order order = orderService.findByOrderCode(orderCode);
        if (order.getTotalPrice() == price){
            PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod(paymentMethod);
            String pay = paymentStrategy.pay(order.getTotalPrice());

            if (pay.equals("success")){
                return orderService.statusOrder(orderCode, OrderStatus.COMPLETED).name();

            }else if (pay.equals("fail")){
                return orderService.statusOrder(orderCode,OrderStatus.CANCELED).name();
            }else
                throw new BadRequestException("Fail");
        }else
            throw new BadRequestException("Wrong Price Try Again");
    }

}
