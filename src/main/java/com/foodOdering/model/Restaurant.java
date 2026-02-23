package com.foodOdering.model;
import java.util.HashMap;
import java.util.Map;
public class Restaurant {

    private final long restaurantId;
    private final String name;
    private final String gstNumber;
    private final String email;
    private final String phoneNumber;

    private final Map<String, Item> catalog = new HashMap<>();

    public Restaurant(long restaurantId, String name,
                      String gstNumber, String email, String phoneNumber) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.gstNumber = gstNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public Map<String, Item> getCatalog() {
        return catalog;
    }

    public void addItem(String itemName, double price, int quantity) {

        if (price <= 0 || quantity <= 0) {
            throw new IllegalArgumentException("Price and quantity must be positive.");
        }

        if (catalog.containsKey(itemName)) {
            catalog.get(itemName).increaseQuantity(quantity);
        } else {
            catalog.put(itemName, new Item(itemName, price, quantity));
        }
    }
}
