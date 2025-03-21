package com.example.ecommercebasic.entity.product;

import com.example.ecommercebasic.entity.file.CoverImage;
import com.example.ecommercebasic.entity.file.ProductImage;
import com.example.ecommercebasic.entity.product.attribute.Attribute;
import com.example.ecommercebasic.entity.product.attribute.ProductAttribute;
import com.example.ecommercebasic.entity.product.order.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
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
    @Enumerated(EnumType.STRING)
    private ProductType productType;
    private String productCode;
    private String description;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discountPrice;
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


    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductAttribute> productAttribute = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isDeleted = false;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cover_image_id")  // Bu, CoverImage tablosundaki foreign key olacak
    private CoverImage coverImage;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();



    public Product(String productName, String description, int quantity, BigDecimal price, boolean status, UnitType unitType) {
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.unitType = unitType;
        this.productType= ProductType.BASIC;
        this.isDeleted = false;
    }

    public Product(String productName, String description,String productCode, int quantity, BigDecimal price, BigDecimal discountPrice, boolean status, UnitType unitType) {
        this.productName = productName;
        this.description = description;
        this.productCode = productCode;
        this.quantity = quantity;
        this.price = price;
        this.discountPrice = discountPrice;
        this.status = status;
        this.unitType = unitType;
        this.productType= ProductType.BASIC;
        this.isDeleted = false;
    }

    public Product(String productName, String productLinkName, String productCode, String description, BigDecimal price, BigDecimal discountPrice, boolean status, UnitType unitType, Set<Category> categories, String coverUrl, List<String> images, List<ProductAttribute> productAttribute) {
        this.productName = productName;
        this.productLinkName = productLinkName;
        this.productCode = productCode;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
        this.status = status;
        this.unitType = unitType;
        this.categories = categories;
        this.productAttribute = productAttribute;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public List<ProductAttribute> getAttributes() {
        return productAttribute;
    }

    public void setAttributes(List<ProductAttribute> productAttribute) {
        this.productAttribute = productAttribute;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }
}
