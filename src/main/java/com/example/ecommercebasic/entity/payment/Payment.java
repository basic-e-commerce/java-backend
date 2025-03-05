package com.example.ecommercebasic.entity.payment;

import com.example.ecommercebasic.entity.product.order.Order;
import jakarta.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_seq", allocationSize = 1)
    private long id;
    private String name;
    private String surname;
    private String username;
    private String phoneNo;
    private String identityNo;

    private String country;
    private String city;
    private String zipCode;

    private String cardHolderName;
    private String cardNumber;

    private String conversationId;
    private String paymentUniqId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // SUCCESS, FAILED gibi durumlar

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // Hangi siparişe ait olduğu

    public Payment(String name, String surname, String username, String phoneNo, String identityNo, String country, String city, String zipCode, String cardHolderName, String cardNumber, String conversationId, String paymentUniqId, PaymentStatus status, Order order) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.phoneNo = phoneNo;
        this.identityNo = identityNo;
        this.country = country;
        this.city = city;
        this.zipCode = zipCode;
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.conversationId = conversationId;
        this.paymentUniqId = paymentUniqId;
        this.status = status;
        this.order = order;
    }

    public Payment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getPaymentUniqId() {
        return paymentUniqId;
    }

    public void setPaymentUniqId(String paymentUniqId) {
        this.paymentUniqId = paymentUniqId;
    }
}
