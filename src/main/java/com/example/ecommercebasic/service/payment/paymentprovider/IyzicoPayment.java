package com.example.ecommercebasic.service.payment.paymentprovider;

import com.example.ecommercebasic.builder.payment.PaymentBuilder;
import com.example.ecommercebasic.dto.payment.InstallmentInfoDto;
import com.example.ecommercebasic.dto.payment.PayCallBackDto;
import com.example.ecommercebasic.dto.payment.ProcessCreditCardDto;
import com.example.ecommercebasic.dto.product.order.OrderDeliveryRequestDto;
import com.example.ecommercebasic.dto.product.payment.CreditCardRequestDto;
import com.example.ecommercebasic.dto.product.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebasic.entity.payment.Payment;
import com.example.ecommercebasic.entity.product.order.Order;
import com.example.ecommercebasic.entity.product.order.OrderItem;
import com.example.ecommercebasic.exception.BadRequestException;
import com.example.ecommercebasic.service.payment.PaymentStrategy;
import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.model.Currency;
import com.iyzipay.model.Locale;
import com.iyzipay.request.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeoutException;

@Service
public class IyzicoPayment implements PaymentStrategy {




    @Value("${payment.iyzico.apiKey}")
    private String apiKey = "sandbox-JbYzNd3TVSGRKgrKKFiM5Ha7MJP7YZSo";

    @Value("${payment.iyzico.secretKey}")
    private String apiSecret = "sandbox-mvXUSAUVAUhj7pNFFsbrKvWjGL5cEaUP";

    @Value("${payment.iyzico.baseUrl}")
    private String apiUrl;

    @Value("${domain.test}")
    private String baseUrl ="https://sandbox-api.iyzipay.com";

    @Value("${payment.iyzico.callBack}")
    private String callBackUrl = "/api/v1/payment/payCallBack";

    @Retryable(
            value = {IOException.class, TimeoutException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000) // 2 saniye bekleyerek yeniden dene
    )  // Eğer ödeme sağlayıcısı geçici bir hata döndürürse (örneğin, zaman aşımı ya da bağlantı hatası), Spring otomatik olarak 3 defa yeniden deneyecek.
    @Override
    public ProcessCreditCardDto processCreditCardPayment(BigDecimal topAmount,Order order, PaymentCreditCardRequestDto paymentCreditCardRequestDto, String conversationId, HttpServletRequest httpServletRequest) {
        System.out.println("toplam: "+topAmount);
        System.out.println(7);
        Options options = getOptions();

        CreatePaymentRequest request = getCreatePaymentRequest(order,conversationId,paymentCreditCardRequestDto.getTotalPrice(),paymentCreditCardRequestDto.getInstallmentNumber());
        System.out.println(8);
        PaymentCard paymentCard = getPaymentCard(paymentCreditCardRequestDto.getCreditCardRequestDto());
        request.setPaymentCard(paymentCard);
        System.out.println(9);
        Buyer buyer = getBuyer(paymentCreditCardRequestDto.getOrderDeliveryRequestDto(),httpServletRequest);
        request.setBuyer(buyer);
        System.out.println(10);
        Address shippingAddress = getShippingAddress(paymentCreditCardRequestDto.getOrderDeliveryRequestDto());
        request.setShippingAddress(shippingAddress);
        System.out.println(11);
        Address billingAddress = getBillingAddress(paymentCreditCardRequestDto.getOrderDeliveryRequestDto());
        request.setBillingAddress(billingAddress);
        System.out.println(12);
        List<BasketItem> basketItems = getBasketItems(order);
        request.setBasketItems(basketItems);
        System.out.println(13);
        ThreedsInitialize threedsInitialize = ThreedsInitialize.create(request, options);
        System.out.println(threedsInitialize);
        System.out.println(14);
        return new ProcessCreditCardDto(
                threedsInitialize.getConversationId(),
                threedsInitialize.getPaymentId(),
                order.getId(),
                threedsInitialize.getHtmlContent(),
                threedsInitialize.getStatus()
        );
    }


    @Override
    public PayCallBackDto payCallBack(Map<String, String> collections) {
        for (Map.Entry<String, String> entry : collections.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        String status = collections.get("status");
        String paymentId = collections.get("paymentId");
        String conversationId = collections.get("conversationId");
        String mdStatus = collections.get("mdStatus");
        System.out.println("mdstatus: "+mdStatus);

        if (!"success".equalsIgnoreCase(status)) {
            return new PayCallBackDto(
                    conversationId,
                    status
            );
        }else{
            System.out.println("Bir kısım başarılı");

            Options options = getOptions();

            CreateThreedsPaymentRequest request = new CreateThreedsPaymentRequest();
            request.setLocale(Locale.TR.getValue());
            request.setConversationId(conversationId);
            request.setPaymentId(paymentId);

            ThreedsPayment threedsPayment = ThreedsPayment.create(request,options);
            System.out.println("status: "+threedsPayment.getStatus());
            System.out.println("paymentId: "+threedsPayment.getPaymentId());
            System.out.println("conversationId: "+threedsPayment.getConversationId());

            if (threedsPayment.getStatus().equals("success")) {
                return new PayCallBackDto(
                        conversationId,
                        status
                );
            }else
                return new PayCallBackDto(
                        conversationId,
                        status
                );
        }

    }

    @Override
    public InstallmentInfoDto getBin(String bin,BigDecimal price) {

        System.out.println("binnnn :" + bin);
        RetrieveBinNumberRequest retrieveBinNumberRequest = new RetrieveBinNumberRequest();
        retrieveBinNumberRequest.setBinNumber(bin);

        Options options = getOptions();

        BinNumber binNumber = BinNumber.retrieve(retrieveBinNumberRequest, options);
        System.out.println("binNumber: "+binNumber);

        if (binNumber.getCardType().equals("CREDIT_CARD")){

            RetrieveInstallmentInfoRequest retrieveInstallmentInfoRequest = new RetrieveInstallmentInfoRequest();
            retrieveInstallmentInfoRequest.setBinNumber(bin);
            retrieveInstallmentInfoRequest.setPrice(price);
            retrieveInstallmentInfoRequest.setCurrency(Currency.TRY.name());

            InstallmentInfo installmentInfo = InstallmentInfo.retrieve(retrieveInstallmentInfoRequest,options);
            System.out.println("installmentInfo: "+installmentInfo);;

            return PaymentBuilder.installmentInfoDto(installmentInfo);
        }else
            throw new BadRequestException("Geçersiz Kart");

    }

    @Override
    public String refund(String paymentId, BigDecimal refundAmount) {
        Options options = getOptions();

        CreateRefundV2Request createRefundV2Request = new CreateRefundV2Request();
        createRefundV2Request.setPaymentId(paymentId);
        createRefundV2Request.setPrice(refundAmount);

        Refund refund = Refund.createV2(createRefundV2Request, options);
        System.out.println(refund.getPaymentId());

        return refund.getPaymentId();
    }


    public Options getOptions() {
        Options options = new Options();
        options.setApiKey(apiKey);
        options.setSecretKey(apiSecret);
        options.setBaseUrl(baseUrl);
        return options;
    }

    public CreatePaymentRequest getCreatePaymentRequest(Order order,String conversationId,BigDecimal totalPrice,Integer installmentNumber) {
        CreatePaymentRequest request = new CreatePaymentRequest();

        request.setLocale(Locale.TR.getValue());
        request.setConversationId(conversationId);
        request.setPrice(order.getTotalPrice());
        request.setPaidPrice(totalPrice);
        request.setCurrency(Currency.TRY.name());
        request.setInstallment(installmentNumber);
        request.setBasketId(order.getOrderCode());
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());
        request.setCallbackUrl("https://litysofttest1.site/api/v1/payment/payCallBack");
        return request;
    }

    public PaymentCard getPaymentCard(CreditCardRequestDto creditCardRequestDto) {
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName(creditCardRequestDto.getCardHolderName());
        paymentCard.setCardNumber(creditCardRequestDto.getCardNumber());
        paymentCard.setExpireMonth(creditCardRequestDto.getExpirationMonth());
        paymentCard.setExpireYear(creditCardRequestDto.getExpirationYear());
        paymentCard.setCvc(creditCardRequestDto.getCvv());
        paymentCard.setRegisterCard(0);

        return paymentCard;
    }

    public Buyer getBuyer(OrderDeliveryRequestDto orderDeliveryRequestDto,HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getRemoteAddr();
        }

        Buyer buyer = new Buyer();
        buyer.setId("BY789");
        buyer.setName(orderDeliveryRequestDto.getName());
        buyer.setSurname(orderDeliveryRequestDto.getSurname());
        buyer.setGsmNumber(orderDeliveryRequestDto.getPhone());
        buyer.setEmail(orderDeliveryRequestDto.getEmail());
        buyer.setIdentityNumber(orderDeliveryRequestDto.getIdentityNo());
        //buyer.setLastLoginDate("2015-10-05 12:43:35");
        //buyer.setRegistrationDate("2013-04-21 15:12:09");
        buyer.setRegistrationAddress(orderDeliveryRequestDto.getAddress());
        buyer.setIp(ip);
        buyer.setCity(orderDeliveryRequestDto.getCity());
        buyer.setCountry(orderDeliveryRequestDto.getCountry());
        buyer.setZipCode(orderDeliveryRequestDto.getPostalCode());

        return buyer;
    }

    public Address getShippingAddress(OrderDeliveryRequestDto orderDeliveryRequestDto) {
        Address shippingAddress = new Address();
        shippingAddress.setContactName(orderDeliveryRequestDto.getName() + " " + orderDeliveryRequestDto.getSurname());
        shippingAddress.setCity(orderDeliveryRequestDto.getCity());
        shippingAddress.setCountry(orderDeliveryRequestDto.getCountry());
        shippingAddress.setAddress(orderDeliveryRequestDto.getAddress());
        shippingAddress.setZipCode(orderDeliveryRequestDto.getPostalCode());
        return shippingAddress;
    }

    public Address getBillingAddress(OrderDeliveryRequestDto orderDeliveryRequestDto) {
        Address billingAddress = new Address();
        billingAddress.setContactName(orderDeliveryRequestDto.getName() + " " + orderDeliveryRequestDto.getSurname());
        billingAddress.setCity(orderDeliveryRequestDto.getCity());
        billingAddress.setCountry(orderDeliveryRequestDto.getCountry());
        billingAddress.setAddress(orderDeliveryRequestDto.getAddress());
        billingAddress.setZipCode(orderDeliveryRequestDto.getPostalCode());
        return billingAddress;
    }

    private List<BasketItem> getBasketItems(Order order) {
        List<BasketItem> basketItems = new ArrayList<>();
        for (OrderItem orderItems : order.getOrderItems()) {
            BasketItem basketItem = getBasketItem(orderItems);
            System.out.println(orderItems.getProduct().getProductName()+"   "+ orderItems.getcurrentPrice());
            basketItems.add(basketItem);
        }
        return basketItems;
    }

    private BasketItem getBasketItem(OrderItem orderItem) {
        BasketItem basketItem = new BasketItem();
        basketItem.setId(String.valueOf(orderItem.getId()));
        basketItem.setName(orderItem.getProduct().getProductName());
        basketItem.setCategory1((orderItem.getProduct().getCategories().stream().findFirst()).get().getName());
        basketItem.setItemType(BasketItemType.PHYSICAL.name());
        basketItem.setPrice(BigDecimal.valueOf(orderItem.getQuantity()).multiply(orderItem.getcurrentPrice()));
        return basketItem;
    }
}
