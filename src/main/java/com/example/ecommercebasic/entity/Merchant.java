package com.example.ecommercebasic.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "merchant")
public class Merchant {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String address;
}
