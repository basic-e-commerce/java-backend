package com.example.ecommercebasic.entity.product.attribute;

import com.example.ecommercebasic.entity.product.Category;
import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;  // Bu özellik hangi kategoriye ait?

    public Attribute(AttributeType attributeType, String name, Category category) {
        this.attributeType = attributeType;
        this.name = name;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }
}
