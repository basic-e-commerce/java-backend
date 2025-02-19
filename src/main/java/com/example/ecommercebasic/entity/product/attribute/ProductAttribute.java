package com.example.ecommercebasic.entity.product.attribute;

import com.example.ecommercebasic.entity.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "product_attribute")
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attibute_value_seq")
    @SequenceGenerator(name = "attibute_value_seq", sequenceName = "attibute_value_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;  // Hangi ürüne ait?

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;  // Hangi özellik?

    @ManyToOne
    @JoinColumn(name = "attribute_value_id")
    private AttributeValue attributeValue;  // Özelliğin değeri

    public ProductAttribute(Product product, Attribute attribute, AttributeValue attributeValue) {
        this.product = product;
        this.attribute = attribute;
        this.attributeValue = attributeValue;
    }

    public ProductAttribute() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public AttributeValue getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(AttributeValue attributeValue) {
        this.attributeValue = attributeValue;
    }
}
