package com.foodOdering.model;
import com.foodOdering.enums.OrderStatus;
import java.util.Map;

public class Order {

    private final long orderId;
    private final long userId;
    private final String restaurantName;
    private final Map<String, Integer> items;
    private OrderStatus status;

    public Order(long orderId,
                 long userId,
                 String restaurantName,
                 Map<String, Integer> items) {

        this.orderId = orderId;
        this.userId = userId;
        this.restaurantName = restaurantName;
        this.items = items;
        this.status = OrderStatus.CONFIRMED;
    }

    public long getOrderId() { return orderId; }
    public long getUserId() { return userId; }
    public String getRestaurantName() { return restaurantName; }
    public Map<String, Integer> getItems() { return items; }
    public OrderStatus getStatus() { return status; }

    public void cancel() {
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order already cancelled.");
        }
        this.status = OrderStatus.CANCELLED;
    }
}