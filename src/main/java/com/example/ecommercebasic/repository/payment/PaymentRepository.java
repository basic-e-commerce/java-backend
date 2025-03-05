package com.example.ecommercebasic.repository.payment;

import com.example.ecommercebasic.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByConversationId(String conversationId);
    Optional<Payment> findByPaymentUniqId(String paymentId);
}
