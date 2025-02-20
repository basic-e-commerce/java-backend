package com.example.ecommercebasic.entity.product.attribute;

import com.example.ecommercebasic.entity.product.Category;
import com.example.ecommercebasic.entity.product.Product;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "attribute_value_mapping",
            joinColumns = @JoinColumn(name = "attribute_id"),
            inverseJoinColumns = @JoinColumn(name = "value_id")
    )
    private List<AttributeValue> values = new ArrayList<>();

    @ManyToMany(mappedBy = "attributes")
    private List<Product> products = new ArrayList<>();

    public Attribute(AttributeType attributeType, String name) {
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

    public List<AttributeValue> getValues() {
        return values;
    }

    public void setValues(List<AttributeValue> values) {
        this.values = values;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
