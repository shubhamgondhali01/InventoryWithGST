package com.example.gstbilling.dto;

import java.util.Map;

public class BillRequest {
    private Map<Long, Integer> products; // Key: Product ID, Value: Quantity
    private String saleDate;

    // Getters and Setters
    public Map<Long, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Long, Integer> products) {
        this.products = products;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }
}
