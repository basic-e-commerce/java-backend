package com.example.ecommercebasic.service.payment;


import com.example.ecommercebasic.dto.payment.InstallmentInfoDto;
import com.example.ecommercebasic.dto.product.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebasic.entity.product.order.Order;
import com.example.ecommercebasic.service.product.OrderService;
import com.iyzipay.model.InstallmentInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService {
    private final OrderService orderService;

    public PaymentService(OrderService orderService) {
        this.orderService = orderService;
    }

    public String processCreditCardPayment(PaymentCreditCardRequestDto paymentCreditCardRequestDto, HttpServletRequest httpServletRequest) {
        Order order = orderService.findByOrderCode(paymentCreditCardRequestDto.getOrderCode());

        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod(paymentCreditCardRequestDto.getPaymentMethod());
        return paymentStrategy.processCreditCardPayment(
                order.getTotalPrice(),
                order,
                paymentCreditCardRequestDto.getCreditCardRequestDto(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto(),
                httpServletRequest
        );
    }


    public String payCallBack(Map<String, String> collections) {
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");
        return paymentStrategy.payCallBack(collections);
    }

    public InstallmentInfoDto getBin(String binCode) {
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");
        return paymentStrategy.getBin(binCode);
    }
}
