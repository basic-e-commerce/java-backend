package com.example.ecommercebasic.entity.product.attribute;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "attribute_value")
public class AttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attibute_value_seq")
    @SequenceGenerator(name = "attibute_value_seq", sequenceName = "attibute_value_seq", allocationSize = 1)
    private int id;

    private String value;

    @ManyToMany(mappedBy = "values")
    private List<Attribute> attributes = new ArrayList<>();

    public AttributeValue(String value) {
        this.value = value;
    }

    public AttributeValue() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
