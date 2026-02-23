package com.foodOdering.driver;


import com.foodOdering.model.Item;
import com.foodOdering.model.Order;
import com.foodOdering.repository.*;
import com.foodOdering.service.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodOrderingApplication {

    public static void main(String[] args) {

        UserRepository userRepository = new UserRepository();
        RestaurantRepository restaurantRepository = new RestaurantRepository();
        OrderRepository orderRepository = new OrderRepository();

        UserService userService =
                new UserService(userRepository);

        RestaurantService restaurantService =
                new RestaurantService(restaurantRepository);

        OrderService orderService =
                new OrderService(orderRepository,
                        userService,
                        restaurantService);

        // Restaurant registration
        restaurantService.registerRestaurant(
                "Donald",
                "GST10905804580",
                "donald@mail.com",
                "1234567890"
        );

        restaurantService.addItemsInCatalog("Donald", "Sandwich", 100.0, 4);
        restaurantService.addItemsInCatalog("Donald", "Burger", 250.0, 4);
        restaurantService.addItemsInCatalog("Donald", "Pizza", 500.0, 10);

        // User registration
        long userId =
                userService.registerUser("User1",
                        "user@mail.com",
                        "1234567890");

        // Search item
        List<Item> searchedItems =
                restaurantService.searchItem("Donald", "Sandwich");

        for (Item item : searchedItems) {
            System.out.println(item.getName()
                    + " , " + item.getPrice()
                    + " , " + item.getQuantity());
        }
        System.out.println(" ");
        // Place Order
        Map<String, Integer> items = new HashMap<>();
        items.put("Sandwich", 2);
        items.put("Noodles", 1);


        long orderId = orderService.placeOrder(userId,
                "Donald",
                items);
        // Get Orders
        List<Order> orders =
                orderService.getOrders(userId);

        for (Order order : orders) {

            for (Map.Entry<String, Integer> entry :
                    order.getItems().entrySet()) {

                System.out.println(order.getOrderId() + " , " + entry.getKey() + " , "
                        + entry.getValue() + " , " + order.getStatus());
            }
            System.out.println(" ");
        }

        // Cancel Order
        orderService.cancelOrder(orderId);

        System.out.println("Status after cancellation:");

        orders = orderService.getOrders(userId);
        for (Order order : orders) {

            for (Map.Entry<String, Integer> entry :
                    order.getItems().entrySet()) {

                System.out.println(order.getOrderId() + " , " + entry.getKey() + " "
                        + entry.getValue() + " " + order.getStatus());
            }
        }
    }
}

