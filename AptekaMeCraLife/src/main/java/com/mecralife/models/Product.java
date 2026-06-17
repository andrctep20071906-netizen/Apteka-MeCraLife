package com.mecralife.models;

public class Product {
    private int id;
    private String name;
    private String mnn;
    private String manufacturer;
    private double price;
    private String form;
    private boolean inStock;
    private String description;
    private String imageUrl;
    private int bonusPercent;
    private String category;

    public Product() {
    }

    public Product(int id, String name, String mnn, String manufacturer,
                   double price, String form, boolean inStock, String description) {
        this.id = id;
        this.name = name;
        this.mnn = mnn;
        this.manufacturer = manufacturer;
        this.price = price;
        this.form = form;
        this.inStock = inStock;
        this.description = description;
        this.bonusPercent = 5;
        this.category = "Другое";
    }

    public Product(int id, String name, String mnn, String manufacturer,
                   double price, String form, boolean inStock, String description, String category) {
        this.id = id;
        this.name = name;
        this.mnn = mnn;
        this.manufacturer = manufacturer;
        this.price = price;
        this.form = form;
        this.inStock = inStock;
        this.description = description;
        this.bonusPercent = 5;
        this.category = category;
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

    public String getMnn() {
        return mnn;
    }

    public void setMnn(String mnn) {
        this.mnn = mnn;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getBonusPercent() {
        return bonusPercent;
    }

    public void setBonusPercent(int bonusPercent) {
        this.bonusPercent = bonusPercent;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name + " - " + price + " руб.";
    }
}