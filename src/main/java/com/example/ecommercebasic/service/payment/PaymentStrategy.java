package com.example.ecommercebasic.service.payment;

import com.example.ecommercebasic.dto.product.payment.CreditCardRequestDto;
import com.example.ecommercebasic.entity.product.order.Order;

public interface PaymentStrategy {
    String processCreditCardPayment(double amount, Order order, CreditCardRequestDto creditCardRequestDto);

}
