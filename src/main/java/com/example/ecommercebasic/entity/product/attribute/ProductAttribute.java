package com.example.ecommercebasic.entity.product.attribute;

import com.example.ecommercebasic.entity.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product_attribute")
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_attribute_seq")
    @SequenceGenerator(name = "product_attribute_seq", sequenceName = "product_attribute_seq", allocationSize = 1)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    @ManyToMany
    @JoinTable(
            name = "product_attribute_attribute_value",
            joinColumns = @JoinColumn(name = "product_attribute_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_value_id")
    )
    private Set<AttributeValue> attributeValues = new HashSet<>();

    public ProductAttribute(Product product, Attribute attribute, Set<AttributeValue> attributeValue) {
        this.product = product;
        this.attribute = attribute;
        this.attributeValues = attributeValue;
    }

    public ProductAttribute() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Set<AttributeValue> getAttributeValue() {
        return attributeValues;
    }

    public void setAttributeValue(Set<AttributeValue> attributeValue) {
        this.attributeValues = attributeValue;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
