package com.example.ecommercebasic.entity.product;

import com.example.ecommercebasic.entity.product.attribute.ProductAttribute;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private int id;
    private String productName;
    private String productLinkName;

    private String productCode;
    private String description;
    private int quantity;
    private float price;
    @Column(columnDefinition = "FLOAT DEFAULT 0")
    private float discountPrice;
    private boolean status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'KILOGRAM'")
    private UnitType unitType;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "category_product",
            joinColumns = @JoinColumn(name = "product_id"), // Bu taraf Product'a ait olmalı
            inverseJoinColumns = @JoinColumn(name = "category_id") // Bu taraf Category'e ait olmalı
    )
    private Set<Category> categories = new HashSet<>();
    private String coverUrl;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductAttribute> productAttributes = new ArrayList<>();

    public Product(String productName, String description, int quantity, float price, boolean status, UnitType unitType) {
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.unitType = unitType;
    }

    public Product(String productName, String description, int quantity, float price, float discountPrice, boolean status, UnitType unitType) {
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.discountPrice = discountPrice;
        this.status = status;
        this.unitType = unitType;
    }

    public Product() {}

    @PrePersist
    @PreUpdate
    private void generateProductData() {
        if (productName != null) {
            this.productLinkName = productName.trim().toLowerCase()
                    .replaceAll("\\s+", "-"); // Boşlukları "-" ile değiştir
        }

        if (productCode == null || productCode.isEmpty()) {
            this.productCode = UUID.randomUUID().toString();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public String getProductLinkName() {
        return productLinkName;
    }

    public void setProductLinkName(String productLinkName) {
        this.productLinkName = productLinkName;
    }

    public List<ProductAttribute> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(List<ProductAttribute> productAttributes) {
        this.productAttributes = productAttributes;
    }
}
