package com.foodOdering.model;

public class Item {
    private final String name;
    private double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int qty) {
        this.quantity += qty;
    }

    public void reduceQuantity(int qty) {
        if (qty > quantity) {
            throw new IllegalArgumentException("Insufficient quantity available.");
        }
        this.quantity -= qty;
    }
}
