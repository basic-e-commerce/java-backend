package com.example.ecommercebasic.controller.product;


import com.example.ecommercebasic.exception.BadRequestException;
import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import com.iyzipay.request.CreateThreedsPaymentRequest;
import com.iyzipay.request.CreateThreedsPaymentRequestV2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/iyzico")
@CrossOrigin("*")
public class TestController {

    private final RestTemplate restTemplate;

    public TestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public String test() {

        Options options = new Options();
        options.setApiKey("sandbox-JbYzNd3TVSGRKgrKKFiM5Ha7MJP7YZSo");
        options.setSecretKey("sandbox-mvXUSAUVAUhj7pNFFsbrKvWjGL5cEaUP");
        options.setBaseUrl("https://sandbox-api.iyzipay.com");

        String uuid = UUID.randomUUID().toString();

        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setLocale(Locale.TR.getValue());
        request.setConversationId(uuid);
        request.setPrice(new BigDecimal("6"));
        request.setPaidPrice(new BigDecimal("6"));
        request.setCurrency(Currency.TRY.name());
        request.setInstallment(1);
        request.setBasketId("B67832");
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());
        request.setCallbackUrl("https://litysofttest1.site/iyzico/payCallBack");

        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName("John Doe");
        paymentCard.setCardNumber("5890040000000016");
        paymentCard.setExpireMonth("12");
        paymentCard.setExpireYear("2030");
        paymentCard.setCvc("123");
        paymentCard.setRegisterCard(0);
        request.setPaymentCard(paymentCard);

        Buyer buyer = new Buyer();
        buyer.setId("BY789");
        buyer.setName("John");
        buyer.setSurname("Doe");
        buyer.setGsmNumber("+905350000000");
        buyer.setEmail("email@email.com");
        buyer.setIdentityNumber("74300864791");
        buyer.setLastLoginDate("2015-10-05 12:43:35");
        buyer.setRegistrationDate("2013-04-21 15:12:09");
        buyer.setRegistrationAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        buyer.setIp("85.34.78.112");
        buyer.setCity("Istanbul");
        buyer.setCountry("Turkey");
        buyer.setZipCode("34732");
        request.setBuyer(buyer);

        Address shippingAddress = new Address();
        shippingAddress.setContactName("Jane Doe");
        shippingAddress.setCity("Istanbul");
        shippingAddress.setCountry("Turkey");
        shippingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        shippingAddress.setZipCode("34742");
        request.setShippingAddress(shippingAddress);

        Address billingAddress = new Address();
        billingAddress.setContactName("Jane Doe");
        billingAddress.setCity("Istanbul");
        billingAddress.setCountry("Turkey");
        billingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        billingAddress.setZipCode("34742");
        request.setBillingAddress(billingAddress);

        List<BasketItem> basketItems = new ArrayList<BasketItem>();
        BasketItem firstBasketItem = new BasketItem();
        firstBasketItem.setId("BI101");
        firstBasketItem.setName("Binocular");
        firstBasketItem.setCategory1("Collectibles");
        firstBasketItem.setCategory2("Accessories");
        firstBasketItem.setItemType(BasketItemType.PHYSICAL.name());
        firstBasketItem.setPrice(new BigDecimal("3"));
        basketItems.add(firstBasketItem);

        BasketItem secondBasketItem = new BasketItem();
        secondBasketItem.setId("BI102");
        secondBasketItem.setName("Game code");
        secondBasketItem.setCategory1("Game");
        secondBasketItem.setCategory2("Online Game Items");
        secondBasketItem.setItemType(BasketItemType.VIRTUAL.name());
        secondBasketItem.setPrice(new BigDecimal("2"));
        basketItems.add(secondBasketItem);

        BasketItem thirdBasketItem = new BasketItem();
        thirdBasketItem.setId("BI103");
        thirdBasketItem.setName("Usb");
        thirdBasketItem.setCategory1("Electronics");
        thirdBasketItem.setCategory2("Usb / Cable");
        thirdBasketItem.setItemType(BasketItemType.PHYSICAL.name());
        thirdBasketItem.setPrice(new BigDecimal("1"));

        basketItems.add(thirdBasketItem);
        request.setBasketItems(basketItems);

        ThreedsInitialize threedsInitialize = ThreedsInitialize.create(request, options);

        System.out.println(threedsInitialize);
        System.out.println(threedsInitialize.getHtmlContent());
        return threedsInitialize.getHtmlContent();
    }


    @PostMapping("/payCallBack")
    public ResponseEntity<String> payCallBack(@RequestParam Map<String, String> collections) {

        for (Map.Entry<String, String> entry : collections.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        String status = collections.get("status");
        String paymentId = collections.get("paymentId");
        String conversationId = collections.get("conversationId");
        String mdStatus = collections.get("mdStatus");



        if (!"success".equalsIgnoreCase(status)) {
            return ResponseEntity.badRequest().body("Ödeme başarısız oldu!");
        }else{
            System.out.println("Bir kısım başarılı")
            ;
            Options options = new Options();
            options.setApiKey("sandbox-JbYzNd3TVSGRKgrKKFiM5Ha7MJP7YZSo");
            options.setSecretKey("sandbox-mvXUSAUVAUhj7pNFFsbrKvWjGL5cEaUP");
            options.setBaseUrl("https://sandbox-api.iyzipay.com");

            CreateThreedsPaymentRequest request = new CreateThreedsPaymentRequest();
            request.setLocale(Locale.TR.getValue());
            request.setConversationId(conversationId);
            request.setPaymentId(paymentId);

            ThreedsPayment threedsPayment = ThreedsPayment.create(request,options);
            System.out.println("status: "+threedsPayment.getStatus());
            System.out.println("paymentId: "+threedsPayment.getPaymentId());
            System.out.println("conversationId: "+threedsPayment.getConversationId());



            if (threedsPayment.getStatus().equals("success")) {
                return ResponseEntity.ok("Ödeme başarılı!");
            }else
                throw new BadRequestException("Ödeme Tamamlanamadı");

        }

    }



}
