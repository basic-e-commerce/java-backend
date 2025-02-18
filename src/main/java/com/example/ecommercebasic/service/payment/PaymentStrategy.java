package com.example.ecommercebasic.service.payment;

import com.example.ecommercebasic.dto.payment.InstallmentInfoDto;
import com.example.ecommercebasic.dto.payment.PayCallBackDto;
import com.example.ecommercebasic.dto.payment.ProcessCreditCardDto;
import com.example.ecommercebasic.dto.product.order.OrderDeliveryRequestDto;
import com.example.ecommercebasic.dto.product.payment.CreditCardRequestDto;
import com.example.ecommercebasic.entity.product.order.Order;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PaymentStrategy {
    ProcessCreditCardDto processCreditCardPayment(double amount, Order order, CreditCardRequestDto creditCardRequestDto, OrderDeliveryRequestDto orderDeliveryRequestDto, String conversationId, HttpServletRequest httpServletRequest);
    PayCallBackDto payCallBack(Map<String, String> collections);
    InstallmentInfoDto getBin(String bin);
}
