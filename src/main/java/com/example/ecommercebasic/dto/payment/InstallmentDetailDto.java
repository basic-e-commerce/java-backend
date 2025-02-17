package com.example.ecommercebasic.dto.payment;

import com.iyzipay.model.InstallmentPrice;

import java.util.List;

public class InstallmentDetailDto {
    private String binNumber;
    private double price;
    private String cardType;
    private String cardAssociation;
    private String cardFamilyName;
    private int force3ds;
    private int bankCode;
    private String bankName;
    private int forceCvc;
    private List<InstallmentPrice> installmentPrices;

    public InstallmentDetailDto(String binNumber, double price, String cardType, String cardAssociation, String cardFamilyName, int force3ds, int bankCode, String bankName, int forceCvc, List<InstallmentPrice> installmentPrices) {
        this.binNumber = binNumber;
        this.price = price;
        this.cardType = cardType;
        this.cardAssociation = cardAssociation;
        this.cardFamilyName = cardFamilyName;
        this.force3ds = force3ds;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.forceCvc = forceCvc;
        this.installmentPrices = installmentPrices;
    }

    public String getBinNumber() {
        return binNumber;
    }

    public void setBinNumber(String binNumber) {
        this.binNumber = binNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardAssociation() {
        return cardAssociation;
    }

    public void setCardAssociation(String cardAssociation) {
        this.cardAssociation = cardAssociation;
    }

    public String getCardFamilyName() {
        return cardFamilyName;
    }

    public void setCardFamilyName(String cardFamilyName) {
        this.cardFamilyName = cardFamilyName;
    }

    public int getForce3ds() {
        return force3ds;
    }

    public void setForce3ds(int force3ds) {
        this.force3ds = force3ds;
    }

    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getForceCvc() {
        return forceCvc;
    }

    public void setForceCvc(int forceCvc) {
        this.forceCvc = forceCvc;
    }

    public List<InstallmentPrice> getInstallmentPrices() {
        return installmentPrices;
    }

    public void setInstallmentPrices(List<InstallmentPrice> installmentPrices) {
        this.installmentPrices = installmentPrices;
    }
}
