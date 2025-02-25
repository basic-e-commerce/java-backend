package com.example.ecommercebasic.dto.product.attribute;

import java.util.Map;

public class ProductFilterRequest {
    private Integer categoryId;
    private Double minPrice;
    private Double maxPrice;
    private String sortBy; // "price", "name" vb.
    private String sortDirection; // "asc" veya "desc"
    private Map<String, String> attributes; // {"color": "red", "size": "M"}

    public ProductFilterRequest(Integer categoryId, Double minPrice, Double maxPrice, String sortBy, String sortDirection, Map<String, String> attributes) {
        this.categoryId = categoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        this.attributes = attributes;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    // Getter ve Setter
}
