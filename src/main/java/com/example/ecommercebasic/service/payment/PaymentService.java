package com.example.ecommercebasic.service.payment;

import com.example.ecommercebasic.dto.product.payment.CreditCardRequestDto;
import com.example.ecommercebasic.entity.product.order.Order;
import com.example.ecommercebasic.entity.product.order.OrderStatus;
import com.example.ecommercebasic.exception.BadRequestException;
import com.example.ecommercebasic.service.product.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final OrderService orderService;

    public PaymentService(OrderService orderService) {
        this.orderService = orderService;
    }

    public String processCreditCardPayment(String paymentMethod, String orderCode,CreditCardRequestDto creditCardRequestDto) {
        Order order = orderService.findByOrderCode(orderCode);

        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod(paymentMethod);
        String pay = paymentStrategy.processCreditCardPayment(order.getTotalPrice(),order,creditCardRequestDto);

        if (pay.equals("success")){
            return orderService.statusOrder(orderCode, OrderStatus.COMPLETED).name();
        }else if (pay.equals("fail")){
            return orderService.statusOrder(orderCode,OrderStatus.CANCELED).name();
        }else
            throw new BadRequestException("Fail");

    }

}
