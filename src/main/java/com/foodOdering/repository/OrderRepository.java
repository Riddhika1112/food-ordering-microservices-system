package com.foodOdering.repository;

import com.foodOdering.model.Order;

import java.util.*;

public class OrderRepository {

    private final Map<Long, Order> orders = new HashMap<>();

    public void save(Order order) {
        orders.put(order.getOrderId(), order);
    }

    public Order findById(long orderId) {
        return orders.get(orderId);
    }

    public List<Order> findByUserId(long userId) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getUserId() == userId) {
                result.add(order);
            }
        }
        return result;
    }
}