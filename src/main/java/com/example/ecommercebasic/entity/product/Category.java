package com.example.ecommercebasic.entity.product;

import com.example.ecommercebasic.entity.product.attribute.Attribute;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    private String coverUrl;

    @ManyToOne
    @JoinColumn(name = "parent_id") // Veritabanındaki sütun adı
    @JsonIgnore
    private Category parentCategory;

    // Alt kategoriler
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> subCategories = new HashSet<>();

    @ManyToMany(mappedBy = "categories", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    // İlişkinin ters tarafı
    private Set<Product> products = new HashSet<>();

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isSubCategory;

    @OneToMany
    private Set<Attribute> attributes = new HashSet<>();

    public Category() {
    }

    public Category(String name,Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
        this.isSubCategory = true;
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

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public boolean isSubCategory() {
        return isSubCategory;
    }

    public void setSubCategory(boolean subCategory) {
        isSubCategory = subCategory;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }
}
