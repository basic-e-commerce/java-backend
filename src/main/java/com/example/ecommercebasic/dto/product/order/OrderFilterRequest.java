package com.example.ecommercebasic.dto.product.order;

public class OrderFilterRequest {
    private String sortBy; // "price", "name" vb.
    private String sortDirection; // "asc" veya "desc"
    private String paymentStatus;

    public OrderFilterRequest(String sortBy, String sortDirection, String paymentStatus) {
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        this.paymentStatus = paymentStatus;
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

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
