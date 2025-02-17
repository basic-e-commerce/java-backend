package com.example.ecommercebasic.dto.payment;

import com.iyzipay.model.InstallmentDetail;

import java.util.List;

public class InstallmentInfoDto {
    private String status;
    private String errorCode;
    private String errorMessage;
    private String errorGroup;
    private String locale;
    private long systemTime;
    private String conversationId;
    private List<InstallmentDetailDto> installmentDetails;

    public InstallmentInfoDto(String status, String errorCode, String errorMessage, String errorGroup, String locale, long systemTime, String conversationId, List<InstallmentDetailDto> installmentDetails) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorGroup = errorGroup;
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

    public List<InstallmentDetailDto> getInstallmentDetails() {
        return installmentDetails;
    }

    public void setInstallmentDetails(List<InstallmentDetailDto> installmentDetails) {
        this.installmentDetails = installmentDetails;
    }

    public String getErrorGroup() {
        return errorGroup;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorGroup(String errorGroup) {
        this.errorGroup = errorGroup;
    }
}
