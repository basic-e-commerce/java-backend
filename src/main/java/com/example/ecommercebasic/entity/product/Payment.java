package com.example.ecommercebasic.entity.product;

import jakarta.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_seq", allocationSize = 1)
    private long id;
    private float amount;

    private String paymentMethod;





}
