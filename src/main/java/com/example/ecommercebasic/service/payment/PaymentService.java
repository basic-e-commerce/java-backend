package com.example.ecommercebasic.service.payment;


import com.example.ecommercebasic.dto.product.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebasic.entity.product.order.Order;
import com.example.ecommercebasic.service.product.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    public ResponseEntity<String> payCallBack(Map<String, String> collections) {
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");
        String payCallBack = paymentStrategy.payCallBack(collections);

        if (payCallBack.equals("Ödeme başarılı!")){
            return new ResponseEntity<>(payCallBack, HttpStatus.OK);
        }else
            return new ResponseEntity<>(payCallBack, HttpStatus.BAD_REQUEST);

    }
}
