package com.example.ecommercebasic.dto.payment;

import java.math.BigDecimal;

public class InstallmentPriceDto {

    private BigDecimal installmentPrice;
    private BigDecimal totalPrice;
    private int installmentNumber;

    public InstallmentPriceDto(BigDecimal installmentPrice, BigDecimal totalPrice, int installmentNumber) {
        this.installmentPrice = installmentPrice;
        this.totalPrice = totalPrice;
        this.installmentNumber = installmentNumber;
    }

    public BigDecimal getInstallmentPrice() {
        return installmentPrice;
    }

    public void setInstallmentPrice(BigDecimal installmentPrice) {
        this.installmentPrice = installmentPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(int installmentNumber) {
        this.installmentNumber = installmentNumber;
    }
}
