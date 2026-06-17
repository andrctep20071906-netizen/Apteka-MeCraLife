package com.mecralife.services;

import com.mecralife.models.CartItem;
import com.mecralife.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartSingleton {
    private static CartSingleton instance;
    private List<CartItem> items;

    private CartSingleton() {
        items = new ArrayList<>();
    }

    public static CartSingleton getInstance() {
        if (instance == null) {
            instance = new CartSingleton();
        }
        return instance;
    }

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) return;

        Optional<CartItem> existing = items.stream()
                .filter(item -> item.getProduct().getId() == product.getId())
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + quantity);
        } else {
            items.add(new CartItem(product, quantity));
        }
    }

    public void removeProduct(int productId) {
        items.removeIf(item -> item.getProduct().getId() == productId);
    }

    public void updateQuantity(int productId, int newQuantity) {
        if (newQuantity <= 0) {
            removeProduct(productId);
            return;
        }
        findItem(productId).ifPresent(item -> item.setQuantity(newQuantity));
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public int getTotalItemsCount() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    private Optional<CartItem> findItem(int productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst();
    }
}