package com.example.ecommercebasic.service.payment;

import com.example.ecommercebasic.service.payment.paymentprovider.IyzicoPayment;

public class PaymentFactory {
    public static PaymentStrategy getPaymentMethod(String paymentMethod) {
        return switch (paymentMethod.toUpperCase()) {
            case "IYZICO" -> new IyzicoPayment();
            default -> throw new IllegalArgumentException("Unknown payment method: " + paymentMethod);
        };
    }
}
