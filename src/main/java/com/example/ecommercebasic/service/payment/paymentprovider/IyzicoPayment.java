package com.example.ecommercebasic.service.payment.paymentprovider;

import com.example.ecommercebasic.builder.payment.PaymentBuilder;
import com.example.ecommercebasic.dto.payment.InstallmentInfoDto;
import com.example.ecommercebasic.dto.product.order.OrderDeliveryRequestDto;
import com.example.ecommercebasic.dto.product.payment.CreditCardRequestDto;
import com.example.ecommercebasic.entity.product.order.Order;
import com.example.ecommercebasic.entity.product.order.OrderItem;
import com.example.ecommercebasic.exception.BadRequestException;
import com.example.ecommercebasic.service.payment.PaymentStrategy;
import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.model.Currency;
import com.iyzipay.model.Locale;
import com.iyzipay.request.CreatePaymentRequest;
import com.iyzipay.request.CreateThreedsPaymentRequest;
import com.iyzipay.request.RetrieveBinNumberRequest;
import com.iyzipay.request.RetrieveInstallmentInfoRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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


    @Override
    public String processCreditCardPayment(double amount, Order order, CreditCardRequestDto creditCardRequestDto, OrderDeliveryRequestDto orderDeliveryRequestDto, HttpServletRequest httpServletRequest) {
        System.out.println("toplam: "+order.getTotalPrice());
        Options options = getOptions();

        CreatePaymentRequest request = getCreatePaymentRequest(order);
        PaymentCard paymentCard = getPaymentCard(creditCardRequestDto);
        request.setPaymentCard(paymentCard);

        Buyer buyer = getBuyer(orderDeliveryRequestDto,httpServletRequest);
        request.setBuyer(buyer);

        Address shippingAddress = getShippingAddress(orderDeliveryRequestDto);
        request.setShippingAddress(shippingAddress);

        Address billingAddress = getBillingAddress(orderDeliveryRequestDto);
        request.setBillingAddress(billingAddress);

        List<BasketItem> basketItems = getBasketItems(order);
        request.setBasketItems(basketItems);

        ThreedsInitialize threedsInitialize = ThreedsInitialize.create(request, options);
        System.out.println(threedsInitialize);

        return threedsInitialize.getHtmlContent();
    }

    @Override
    public String payCallBack(Map<String, String> collections) {
        for (Map.Entry<String, String> entry : collections.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        String status = collections.get("status");
        String paymentId = collections.get("paymentId");
        String conversationId = collections.get("conversationId");
        String mdStatus = collections.get("mdStatus");

        if (!"success".equalsIgnoreCase(status)) {
            return "Ödeme başarısız oldu!";
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
                return "Ödeme başarılı!";
            }else
                throw new BadRequestException("Ödeme Tamamlanamadı");
        }

    }

    @Override
    public InstallmentInfoDto getBin(String bin) {

        System.out.println("binnnn :" + bin);
        RetrieveBinNumberRequest retrieveBinNumberRequest = new RetrieveBinNumberRequest();
        retrieveBinNumberRequest.setBinNumber(bin);

        Options options = getOptions();

        BinNumber binNumber = BinNumber.retrieve(retrieveBinNumberRequest, options);
        System.out.println("binNumber: "+binNumber);

        if (binNumber.getCardType().equals("CREDIT_CARD")){

            RetrieveInstallmentInfoRequest retrieveInstallmentInfoRequest = new RetrieveInstallmentInfoRequest();
            retrieveInstallmentInfoRequest.setBinNumber(bin);
            retrieveInstallmentInfoRequest.setPrice(BigDecimal.valueOf(10000));
            retrieveInstallmentInfoRequest.setCurrency(Currency.TRY.name());

            InstallmentInfo installmentInfo = InstallmentInfo.retrieve(retrieveInstallmentInfoRequest,options);
            System.out.println("installmentInfo: "+installmentInfo);;

            return PaymentBuilder.installmentInfoDto(installmentInfo);
        }else
            throw new BadRequestException("Geçersiz Kart");

    }

//    // HMACSHA256 hash ve Base64 encode işlemi
//    public  String generateAuthorizationString(String code) throws Exception {
//        String requestData = "{\"installmentNumber\":"+code+",\"totalPrice\":10000,\"locale\":\"tr\",\"conversationId\":\"123456789\"}";
//
//        String secretKey = "sandbox-mvXUSAUVAUhj7pNFFsbrKvWjGL5cEaUP";
//        String apiKey = "sandbox-JbYzNd3TVSGRKgrKKFiM5Ha7MJP7YZSo";
//
//        String uriPath = "/payment/iyzipos/installment";
//        String payload = "123456789" + uriPath + requestData;
//
//        Mac mac = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//        mac.init(secretKeySpec);
//        byte[] encryptedDataBytes = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
//
//        String encryptedData = Base64.getEncoder().encodeToString(encryptedDataBytes);
//
//        String authorizationString = "apiKey:" + apiKey
//                + "&randomKey:" + "123456789"
//                + "&signature:" + encryptedData;
//
//        return "IYZWSv2 " + Base64.getEncoder().encodeToString(authorizationString.getBytes(StandardCharsets.UTF_8));
//
//
//
//        /**
//        // Benzersiz randomKey oluşturma (zaman damgası ve sabit bir string)
//        String randomKey = System.currentTimeMillis() + "123456789";
//
//        // URI path örneği: "/payment/bin/check"
//        String uriPath = "/payment/bin/check";
//
//        // Payload oluşturma
//        String payload = (requestData == null || requestData.isEmpty())
//                ? randomKey + uriPath
//                : randomKey + uriPath + requestData;
//
//        // HMACSHA256 işlemi
//        Mac mac = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
//        mac.init(secretKeySpec);
//        byte[] encryptedDataBytes = mac.doFinal(payload.getBytes());
//
//        // Base64 encoding
//        String encryptedData = Base64.getEncoder().encodeToString(encryptedDataBytes);
//
//        // Authorization string oluşturma
//        String authorizationString = "apiKey:" + apiKey
//                + "&randomKey:" + randomKey
//                + "&signature:" + encryptedData;
//
//        // Base64 encoding
//        String base64EncodedAuthorization = Base64.getEncoder().encodeToString(authorizationString.getBytes());
//
//        // Sonuç: IYZWSv2 + base64EncodedAuthorization
//        return "IYZWSv2 " + base64EncodedAuthorization;
//
//        **/
//    }


    public Options getOptions() {
        Options options = new Options();
        options.setApiKey(apiKey);
        options.setSecretKey(apiSecret);
        options.setBaseUrl(baseUrl);
        return options;
    }

    public CreatePaymentRequest getCreatePaymentRequest(Order order) {
        String uuid = UUID.randomUUID().toString();
        CreatePaymentRequest request = new CreatePaymentRequest();

        request.setLocale(Locale.TR.getValue());
        request.setConversationId(uuid);
        request.setPrice(BigDecimal.valueOf(order.getTotalPrice()));
        request.setPaidPrice(BigDecimal.valueOf(order.getTotalPrice()));
        request.setCurrency(Currency.TRY.name());
        request.setInstallment(1);
        request.setBasketId(order.getOrderCode());
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());
        request.setCallbackUrl("https://litysofttest1.site"+"/api/v1/payment/payCallBack");
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
        basketItem.setPrice(BigDecimal.valueOf(orderItem.getcurrentPrice()*orderItem.getQuantity()));
        return basketItem;
    }
}
