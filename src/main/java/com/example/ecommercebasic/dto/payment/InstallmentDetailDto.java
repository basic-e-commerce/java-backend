package com.example.ecommercebasic.dto.payment;

import com.iyzipay.model.InstallmentPrice;

import java.math.BigDecimal;
import java.util.List;

public class InstallmentDetailDto {
    private String binNumber;
    private BigDecimal price;
    private String cardType;
    private String cardAssociation;
    private String cardFamilyName;
    private int force3ds;
    private long bankCode;
    private String bankName;
    private int forceCvc;
    private int commercial;
    private List<InstallmentPriceDto> installmentPrices;

    public InstallmentDetailDto(String binNumber, BigDecimal price, String cardType, String cardAssociation, String cardFamilyName, int force3ds, long bankCode, String bankName, int forceCvc, int commercial, List<InstallmentPriceDto> installmentPrices) {
        this.binNumber = binNumber;
        this.price = price;
        this.cardType = cardType;
        this.cardAssociation = cardAssociation;
        this.cardFamilyName = cardFamilyName;
        this.force3ds = force3ds;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.forceCvc = forceCvc;
        this.commercial = commercial;
        this.installmentPrices = installmentPrices;
    }

    public String getBinNumber() {
        return binNumber;
    }

    public void setBinNumber(String binNumber) {
        this.binNumber = binNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public long getBankCode() {
        return bankCode;
    }

    public void setBankCode(long bankCode) {
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

    public List<InstallmentPriceDto> getInstallmentPrices() {
        return installmentPrices;
    }

    public void setInstallmentPrices(List<InstallmentPriceDto> installmentPrices) {
        this.installmentPrices = installmentPrices;
    }

    public int getCommercial() {
        return commercial;
    }

    public void setCommercial(int commercial) {
        this.commercial = commercial;
    }
}
