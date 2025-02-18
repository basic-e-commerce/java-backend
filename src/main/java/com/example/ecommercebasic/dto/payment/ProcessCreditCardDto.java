package com.example.ecommercebasic.dto.payment;

public class ProcessCreditCardDto {
    private String conversationId;
    private long orderId;
    private String getHtmlContent;
    private String status;

    public ProcessCreditCardDto(String conversationId, long orderId, String getHtmlContent, String status) {
        this.conversationId = conversationId;
        this.orderId = orderId;
        this.getHtmlContent = getHtmlContent;
        this.status = status;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getGetHtmlContent() {
        return getHtmlContent;
    }

    public void setGetHtmlContent(String getHtmlContent) {
        this.getHtmlContent = getHtmlContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
