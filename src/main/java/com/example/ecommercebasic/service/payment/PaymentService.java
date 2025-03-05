package com.example.ecommercebasic.service.payment;


import com.example.ecommercebasic.dto.payment.*;
import com.example.ecommercebasic.dto.product.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebasic.entity.payment.Payment;
import com.example.ecommercebasic.entity.payment.PaymentStatus;
import com.example.ecommercebasic.entity.product.order.Order;
import com.example.ecommercebasic.entity.product.order.OrderStatus;
import com.example.ecommercebasic.exception.BadRequestException;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.exception.ResourceAlreadyExistException;
import com.example.ecommercebasic.repository.payment.PaymentRepository;
import com.example.ecommercebasic.service.product.OrderService;
import com.iyzipay.model.InstallmentDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

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
        PaymentStatus hasSuccessfulPayment = order.getPayments().getStatus();
        if (hasSuccessfulPayment.equals(PaymentStatus.SUCCESS))
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
                conversationId,
                "İslem Baslatılıyor",
                PaymentStatus.PROCESS,
                order
        );
        Payment savePayment = paymentRepository.save(payment);

        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod(paymentCreditCardRequestDto.getPaymentMethod());
        BigDecimal totalPrice = processTotalPrice(order.getTotalPrice());
        String binNumber = paymentCreditCardRequestDto.getCreditCardRequestDto().getCardNumber().substring(0, 6);

        if (paymentCreditCardRequestDto.getInstallmentNumber() != 1) {
            InstallmentInfoDto bin = getBin(binNumber, totalPrice);
            InstallmentPriceDto installmentPrice = getInstallmentPrice(binNumber, bin, paymentCreditCardRequestDto.getInstallmentNumber());
            totalPrice = installmentPrice.getTotalPrice();
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        BigDecimal finalTotalPrice = totalPrice;
        Future<ProcessCreditCardDto> future = executor.submit(() ->
                paymentStrategy.processCreditCardPayment(finalTotalPrice, order, paymentCreditCardRequestDto, conversationId, httpServletRequest)
        );

        try {
            ProcessCreditCardDto processCreditCardDto = future.get(10, TimeUnit.SECONDS);
            savePayment.setPaymentUniqId(processCreditCardDto.getPaymentId());
            paymentRepository.save(savePayment);

            if (processCreditCardDto.getConversationId().equals(conversationId) && processCreditCardDto.getStatus().equals("success")) {
                return processCreditCardDto.getGetHtmlContent();
            } else {
                throw new BadRequestException("Invalid request");
            }

        } catch (TimeoutException e) {
            future.cancel(true);
            throw new BadRequestException("Ödeme işlemi zaman aşımına uğradı.");
        } catch (ExecutionException e) {
            throw new BadRequestException("Ödeme işlemi sırasında hata oluştu: " + e.getCause().getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("İşlem kesildi.");
        } finally {
            executor.shutdown(); // Thread kapanıyor
        }
    }

    public String refund(String paymentId, BigDecimal refundAmount){
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");
        Payment payment = findByPaymentId(paymentId);

        if (!payment.getStatus().equals(PaymentStatus.SUCCESS))
            throw new BadRequestException("İşlem iadeye uygun değildir");

        String refundPaymentId = paymentStrategy.refund(paymentId, refundAmount);
        System.out.println(refundPaymentId);
        Payment payment1 = findByPaymentId(refundPaymentId);
        payment1.setStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment1);

        Order order = orderService.findByPayment(payment);
        order.setStatus(OrderStatus.REDELIVERED);
        orderService.save(order);

        return refundPaymentId;
    }

    // Taksite göre fiyatı ayarlama
    private InstallmentPriceDto getInstallmentPrice(String binNumber, InstallmentInfoDto bin,int installmentNumber) {
        // Bin numarasına sahip InstallmentDetailDto'yu bul
        for (InstallmentDetailDto detail : bin.getInstallmentDetails()) {
            if (detail.getBinNumber().equals(binNumber) && detail.getInstallmentPrices() != null) {

                // İlgili taksit numarasına sahip InstallmentPriceDto'yu bul
                for (InstallmentPriceDto priceDto : detail.getInstallmentPrices()) {
                    if (priceDto.getInstallmentNumber() == installmentNumber) {
                        return priceDto;
                    }
                }
            }
        }
        throw new BadRequestException("Invalid Card");
    }

    private BigDecimal processTotalPrice(BigDecimal totalPrice) {
        BigDecimal kargoPrice = new  BigDecimal("75.00");
        BigDecimal minPrice = new BigDecimal("2000.00");

        if (totalPrice.compareTo(minPrice) < 0) {
            totalPrice.add(kargoPrice);
        }
        return totalPrice;
    }


    public void payCallBack(Map<String, String> collections, HttpServletResponse httpServletResponse) throws IOException {
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");
        PayCallBackDto payCallBackDto = paymentStrategy.payCallBack(collections);

        if (payCallBackDto.getStatus().equals("success")) {
            Payment payment = findByConversationId(payCallBackDto.getConversationId());
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);
            String redirectUrl = "https://litysofttest1.site/success-payment?orderCode=" + payment.getOrder().getOrderCode(); // Query parametreli URL
            httpServletResponse.sendRedirect(redirectUrl);
        }else{
            Payment payment = findByConversationId(payCallBackDto.getConversationId());
            payment.setStatus(PaymentStatus.FAILED);
            String redirectUrl = "https://litysofttest1.site/success-payment?orderCode=" + payment.getOrder().getOrderCode(); // Query parametreli URL
            httpServletResponse.sendRedirect(redirectUrl);
        }
    }

    public InstallmentInfoDto getBin(String binCode,BigDecimal price) {
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");
        return paymentStrategy.getBin(binCode, price);
    }

    public Payment findByConversationId(String conversationId) {
        return paymentRepository.findByConversationId(conversationId).orElseThrow(()-> new NotFoundException("Payment Not Found"));
    }
    public Payment findByPaymentId(String paymentId) {
        return paymentRepository.findByPaymentUniqId(paymentId).orElseThrow(()-> new NotFoundException("Payment Not Found"));
    }


}
