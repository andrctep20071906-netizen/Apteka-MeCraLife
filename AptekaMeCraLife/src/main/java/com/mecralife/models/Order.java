package com.mecralife.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private String orderDate;
    private double totalSum;
    private int bonusSpent;
    private int bonusEarned;
    private String pickupPoint;
    private String status;
    private List<OrderItem> items;

    public Order() {
        this.items = new ArrayList<>();
        this.status = "Новый";
    }

    public Order(int id, int userId, double totalSum, int bonusSpent,
                 int bonusEarned, String pickupPoint) {
        this.id = id;
        this.userId = userId;
        this.orderDate = java.time.LocalDateTime.now().toString();
        this.totalSum = totalSum;
        this.bonusSpent = bonusSpent;
        this.bonusEarned = bonusEarned;
        this.pickupPoint = pickupPoint;
        this.status = "Новый";
        this.items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public int getBonusSpent() {
        return bonusSpent;
    }

    public void setBonusSpent(int bonusSpent) {
        this.bonusSpent = bonusSpent;
    }

    public int getBonusEarned() {
        return bonusEarned;
    }

    public void setBonusEarned(int bonusEarned) {
        this.bonusEarned = bonusEarned;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}