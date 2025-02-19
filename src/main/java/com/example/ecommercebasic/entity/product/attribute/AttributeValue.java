package com.example.ecommercebasic.entity.product.attribute;

import jakarta.persistence.*;

@Entity
@Table(name = "attribute_value")
public class AttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attibute_value_seq")
    @SequenceGenerator(name = "attibute_value_seq", sequenceName = "attibute_value_seq", allocationSize = 1)
    private int id;

    private String value;  // Örneğin, "8GB", "Mavi", "L" vb.

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;  // Bu değer hangi özelliğe ait?

    public AttributeValue(String value, Attribute attribute) {
        this.value = value;
        this.attribute = attribute;
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

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
}
