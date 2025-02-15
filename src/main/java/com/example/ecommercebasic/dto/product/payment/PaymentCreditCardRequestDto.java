package com.example.ecommercebasic.dto.product.payment;

import com.example.ecommercebasic.dto.product.order.OrderDeliveryRequestDto;

public class PaymentCreditCardRequestDto {
    private String paymentMethod;
    private String orderCode;
    private CreditCardRequestDto creditCardRequestDto;
    private OrderDeliveryRequestDto orderDeliveryRequestDto;

    public PaymentCreditCardRequestDto(String paymentMethod, String orderCode, CreditCardRequestDto creditCardRequestDto, OrderDeliveryRequestDto orderDeliveryRequestDto) {
        this.paymentMethod = paymentMethod;
        this.orderCode = orderCode;
        this.creditCardRequestDto = creditCardRequestDto;
        this.orderDeliveryRequestDto = orderDeliveryRequestDto;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public CreditCardRequestDto getCreditCardRequestDto() {
        return creditCardRequestDto;
    }

    public void setCreditCardRequestDto(CreditCardRequestDto creditCardRequestDto) {
        this.creditCardRequestDto = creditCardRequestDto;
    }

    public OrderDeliveryRequestDto getOrderDeliveryRequestDto() {
        return orderDeliveryRequestDto;
    }

    public void setOrderDeliveryRequestDto(OrderDeliveryRequestDto orderDeliveryRequestDto) {
        this.orderDeliveryRequestDto = orderDeliveryRequestDto;
    }
}
