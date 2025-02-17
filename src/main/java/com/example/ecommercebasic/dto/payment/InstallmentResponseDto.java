package com.example.ecommercebasic.dto.payment;

import com.iyzipay.model.InstallmentDetail;

import java.util.List;

public class InstallmentResponseDto {
    private String status;
    private String locale;
    private long systemTime;
    private String conversationId;
    private List<InstallmentDetail> installmentDetails;

    public InstallmentResponseDto(String status, String locale, long systemTime, String conversationId, List<InstallmentDetail> installmentDetails) {
        this.status = status;
        this.locale = locale;
        this.systemTime = systemTime;
        this.conversationId = conversationId;
        this.installmentDetails = installmentDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public List<InstallmentDetail> getInstallmentDetails() {
        return installmentDetails;
    }

    public void setInstallmentDetails(List<InstallmentDetail> installmentDetails) {
        this.installmentDetails = installmentDetails;
    }
}
