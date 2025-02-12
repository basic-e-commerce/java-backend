package com.example.ecommercebasic.service.payment;

import com.example.ecommercebasic.service.payment.paymentmethods.CreditCardPayment;

public class PaymentFactory {
    public static PaymentStrategy getPaymentMethod(String paymentMethod) {
        return switch (paymentMethod.toUpperCase()) {
            case "CREDIT_CARD" -> new CreditCardPayment();
            default -> throw new IllegalArgumentException("Unknown payment method: " + paymentMethod);
        };
    }
}
