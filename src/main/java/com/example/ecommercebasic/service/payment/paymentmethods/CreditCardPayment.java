package com.example.ecommercebasic.service.payment.paymentmethods;

import com.example.ecommercebasic.service.payment.PaymentStrategy;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public String pay(double amount) {
        return "Credit card payment okey";
    }
}
