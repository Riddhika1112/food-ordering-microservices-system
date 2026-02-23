package com.foodOdering.service;

import com.foodOdering.model.Item;
import com.foodOdering.model.Order;
import com.foodOdering.model.Restaurant;
import com.foodOdering.repository.OrderRepository;
import com.foodOdering.exception.ItemNotFound;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final RestaurantService restaurantService;

    private final AtomicLong orderIdGenerator = new AtomicLong(9000);

    public OrderService(OrderRepository orderRepository,
                        UserService userService,
                        RestaurantService restaurantService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    public long placeOrder(long userId,
                           String restaurantName,
                           Map<String, Integer> itemsToOrder) {

        if (itemsToOrder == null || itemsToOrder.isEmpty()) {
            throw new IllegalArgumentException("Order cannot be empty");
        }

        userService.getUser(userId);
        Restaurant restaurant = restaurantService.getRestaurant(restaurantName);

        synchronized (restaurant) {
            // First validation
            for (Map.Entry<String, Integer> entry : itemsToOrder.entrySet()) {

                String itemName = entry.getKey();
                int quantity = entry.getValue();

                if (quantity <= 0) {
                    throw new ItemNotFound("Item not found" + itemName);
                }

                Item item = restaurant.getCatalog().get(itemName);

                if (item == null) {
                    throw new ItemNotFound("Item not found" + itemName);
                }

                if (item.getQuantity() < quantity) {
                    throw new IllegalArgumentException("Insufficient stock for: " + itemName);
                }
            }

            // Deduct inventory
            for (Map.Entry<String, Integer> entry : itemsToOrder.entrySet()) {
                restaurant.getCatalog()
                        .get(entry.getKey())
                        .reduceQuantity(entry.getValue());
            }

            long orderId = orderIdGenerator.incrementAndGet();

            Order order = new Order(orderId, userId,
                    restaurantName, itemsToOrder);

            orderRepository.save(order);

            System.out.println("Order placed successfully orderId: " + orderId);


            return orderId;
        }
    }
    public List<Order> getOrders(long userId) {
        userService.getUser(userId);
        return orderRepository.findByUserId(userId);
    }

    public void cancelOrder(long orderId) {

        Order order = orderRepository.findById(orderId);

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        if (order.getStatus().name().equals("CANCELLED")) {
            throw new IllegalStateException("Order already cancelled");
        }

        Restaurant restaurant =
                restaurantService.getRestaurant(order.getRestaurantName());

        for (Map.Entry<String, Integer> entry : order.getItems().entrySet()) {

            restaurant.getCatalog()
                    .get(entry.getKey())
                    .increaseQuantity(entry.getValue());
        }

        order.cancel();

        System.out.println("Order " + orderId + " canceled successfully");

    }
}
