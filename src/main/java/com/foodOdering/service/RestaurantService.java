package com.foodOdering.service;


import com.foodOdering.model.Item;
import com.foodOdering.model.Restaurant;
import com.foodOdering.repository.RestaurantRepository;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class RestaurantService {

    private final RestaurantRepository repository;
    private final AtomicLong idGenerator = new AtomicLong(5000);

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public void registerRestaurant(String name, String gst,
                                   String email, String phone) {

        if (repository.findByName(name) != null) {
            throw new IllegalArgumentException("Restaurant already exists");
        }

        long id = idGenerator.incrementAndGet();
        Restaurant restaurant =
                new Restaurant(id, name, gst, email, phone);

        repository.save(restaurant);

        System.out.println(" ");
        System.out.println("Registered!!! RestaurantId: " + id);
        System.out.println(" ");
    }

    public void addItemsInCatalog(String restaurantName,
                                  String itemName,
                                  double price,
                                  int quantity) {

        Restaurant restaurant = getRestaurant(restaurantName);
        restaurant.addItem(itemName, price, quantity);
    }

    public List<Item> searchItem(String restaurantName, String itemName) {

        Restaurant restaurant = getRestaurant(restaurantName);

        return restaurant.getCatalog().values()
                .stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .sorted(Comparator.comparingDouble(Item::getPrice))
                .collect(Collectors.toList());
    }

    public Restaurant getRestaurant(String name) {
        Restaurant restaurant = repository.findByName(name);
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant not found");
        }
        return restaurant;
    }
}