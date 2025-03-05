package com.example.ecommercebasic.entity.product.order;

import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.entity.product.UnitType;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq")
    @SequenceGenerator(name = "order_item_seq", sequenceName = "order_item_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;  // Satın alınan ürün

    private int quantity; // Miktar
    private BigDecimal currentPrice; // O anki fiyat

    public OrderItem(Product product, int quantity, BigDecimal currentPrice) {
        this.product = product;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
    }

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getcurrentPrice() {
        return currentPrice;
    }

    public void setcurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}
