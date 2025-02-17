package com.example.ecommercebasic.dto.payment;

public class InstallmentPriceDto {

    private double installmentPrice;
    private double totalPrice;
    private int installmentNumber;

    public InstallmentPriceDto(double installmentPrice, double totalPrice, int installmentNumber) {
        this.installmentPrice = installmentPrice;
        this.totalPrice = totalPrice;
        this.installmentNumber = installmentNumber;
    }

    public double getInstallmentPrice() {
        return installmentPrice;
    }

    public void setInstallmentPrice(double installmentPrice) {
        this.installmentPrice = installmentPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(int installmentNumber) {
        this.installmentNumber = installmentNumber;
    }
}
