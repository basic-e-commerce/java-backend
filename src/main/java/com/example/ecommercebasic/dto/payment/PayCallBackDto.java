package com.example.ecommercebasic.dto.payment;

public class PayCallBackDto {
    private String conversationId;
    private String status;

    public PayCallBackDto(String conversationId, String status) {
        this.conversationId = conversationId;
        this.status = status;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
