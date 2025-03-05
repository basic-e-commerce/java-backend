package com.example.ecommercebasic.service.payment;

import com.example.ecommercebasic.dto.payment.InstallmentInfoDto;
import com.example.ecommercebasic.dto.payment.PayCallBackDto;
import com.example.ecommercebasic.dto.payment.ProcessCreditCardDto;
import com.example.ecommercebasic.dto.product.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebasic.entity.product.order.Order;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.Map;

public interface PaymentStrategy {
    ProcessCreditCardDto processCreditCardPayment(BigDecimal totalAmount,Order order, PaymentCreditCardRequestDto paymentCreditCardRequestDto, String conversationId, HttpServletRequest httpServletRequest);
    PayCallBackDto payCallBack(Map<String, String> collections);
    InstallmentInfoDto getBin(String bin,BigDecimal price);
}
