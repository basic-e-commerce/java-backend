package com.example.ecommercebasic.service.payment;

import com.example.ecommercebasic.dto.product.order.OrderDeliveryRequestDto;
import com.example.ecommercebasic.dto.product.payment.CreditCardRequestDto;
import com.example.ecommercebasic.entity.product.order.Order;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PaymentStrategy {
    String processCreditCardPayment(double amount, Order order, CreditCardRequestDto creditCardRequestDto, OrderDeliveryRequestDto orderDeliveryRequestDto, HttpServletRequest httpServletRequest);
    String payCallBack(Map<String, String> collections);
    String getBin(String bin);
}
