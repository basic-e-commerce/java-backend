package com.example.ecommercebasic.dto.product.productdto;

public class ProductFilterRequest {
    private String sortBy; // "price", "name" vb.
    private String sortDirection; // "asc" veya "desc"

    public ProductFilterRequest(String sortBy, String sortDirection) {
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
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
}
