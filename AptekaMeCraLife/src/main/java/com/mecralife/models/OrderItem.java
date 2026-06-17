package com.mecralife.models;

public class OrderItem {
    private int productId;
    private String productName;
    private int quantity;
    private double priceAtMoment;

    public OrderItem() {
    }

    public OrderItem(int productId, String productName, int quantity, double priceAtMoment) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.priceAtMoment = priceAtMoment;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtMoment() {
        return priceAtMoment;
    }

    public void setPriceAtMoment(double priceAtMoment) {
        this.priceAtMoment = priceAtMoment;
    }

    public double getTotal() {
        return priceAtMoment * quantity;
    }
}