package com.foodOdering.repository;
import com.foodOdering.model.Restaurant;

import java.util.HashMap;
import java.util.Map;

public class RestaurantRepository {

    private final Map<String, Restaurant> restaurants = new HashMap<>();

    public void save(Restaurant restaurant) {
        restaurants.put(restaurant.getName(), restaurant);
    }

    public Restaurant findByName(String name) {
        return restaurants.get(name);
    }
}