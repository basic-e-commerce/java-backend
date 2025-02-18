package com.example.ecommercebasic.service.payment;


import com.example.ecommercebasic.dto.payment.InstallmentInfoDto;
import com.example.ecommercebasic.dto.payment.PayCallBackDto;
import com.example.ecommercebasic.dto.payment.ProcessCreditCardDto;
import com.example.ecommercebasic.dto.product.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebasic.entity.payment.Payment;
import com.example.ecommercebasic.entity.payment.PaymentStatus;
import com.example.ecommercebasic.entity.product.order.Order;
import com.example.ecommercebasic.exception.BadRequestException;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.exception.ResourceAlreadyExistException;
import com.example.ecommercebasic.repository.payment.PaymentRepository;
import com.example.ecommercebasic.service.product.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class PaymentService {
    private final OrderService orderService;
    private final PaymentRepository paymentRepository;

    public PaymentService(OrderService orderService, PaymentRepository paymentRepository) {
        this.orderService = orderService;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public String processCreditCardPayment(PaymentCreditCardRequestDto paymentCreditCardRequestDto, HttpServletRequest httpServletRequest) {
        Order order = orderService.findByOrderCode(paymentCreditCardRequestDto.getOrderCode());
        boolean hasSuccessfulPayment = order.getPayments()
                .stream()
                .anyMatch(payment -> payment.getStatus().equals(PaymentStatus.SUCCESS));

        if (hasSuccessfulPayment)
            throw new ResourceAlreadyExistException("Bu sipariş için zaten başarılı bir ödeme var!");

        String conversationId = UUID.randomUUID().toString();
        Payment payment = new Payment(
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getName(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getSurname(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getEmail(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getPhone(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getIdentityNo(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getCountry(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getCity(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getPostalCode(),
                paymentCreditCardRequestDto.getCreditCardRequestDto().getCardHolderName(),
                paymentCreditCardRequestDto.getCreditCardRequestDto().getCardNumber(),
                conversationId,
                PaymentStatus.PROCESS,
                order
        );
        paymentRepository.save(payment);

        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod(paymentCreditCardRequestDto.getPaymentMethod());
        ProcessCreditCardDto processCreditCardDto = paymentStrategy.processCreditCardPayment(
                order.getTotalPrice(),
                order,
                paymentCreditCardRequestDto.getCreditCardRequestDto(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto(),
                conversationId,
                httpServletRequest
        );

        if (processCreditCardDto.getConversationId().equals(conversationId) && processCreditCardDto.getStatus().equals("success")) {
            return processCreditCardDto.getGetHtmlContent();
        }else
            throw new BadRequestException("Invalid request");
    }


    public String payCallBack(Map<String, String> collections) {
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");
        PayCallBackDto payCallBackDto = paymentStrategy.payCallBack(collections);
        if (payCallBackDto.getStatus().equals("success")) {
            Payment payment = findByConversationId(payCallBackDto.getConversationId());
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);
            return "Ödeme Başarılı";
        }else
            return  "Ödeme başarısız";

    }

    public InstallmentInfoDto getBin(String binCode) {
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");
        return paymentStrategy.getBin(binCode);
    }

    public Payment findByConversationId(String conversationId) {
        return paymentRepository.findByConversationId(conversationId).orElseThrow(()-> new NotFoundException("Payment Not Found"));
    }
}
