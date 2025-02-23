package com.example.ecommercebasic.entity.product.attribute;

import com.example.ecommercebasic.entity.product.Category;
import com.example.ecommercebasic.entity.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "attribute")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attibute_seq")
    @SequenceGenerator(name = "attibute_seq", sequenceName = "attibute_seq", allocationSize = 1)
    private int id;

    @Enumerated(EnumType.STRING)
    private AttributeType attributeType;

    private String name;  // Özelliğin adı (RAM, Renk, Beden vb.)

    @OneToMany(cascade = CascadeType.ALL)
    private Set<AttributeValue> attributeValues = new HashSet<>();

    public Attribute(String name,AttributeType attributeType) {
        this.attributeType = attributeType;
        this.name = name;
    }

    public Attribute() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public Set<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(Set<AttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
