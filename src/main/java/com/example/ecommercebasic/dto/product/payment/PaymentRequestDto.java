package com.example.ecommercebasic.dto.product.payment;

public class PaymentRequestDto {
    private String locale;
    private String conversationId;
    private String paymentId;
    private String paidPrice;
    private String basketId;
    private String currency;

    public PaymentRequestDto(String locale, String conversationId, String paymentId, String paidPrice, String basketId, String currency) {
        this.locale = locale;
        this.conversationId = conversationId;
        this.paymentId = paymentId;
        this.paidPrice = paidPrice;
        this.basketId = basketId;
        this.currency = currency;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(String paidPrice) {
        this.paidPrice = paidPrice;
    }

    public String getBasketId() {
        return basketId;
    }

    public void setBasketId(String basketId) {
        this.basketId = basketId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
