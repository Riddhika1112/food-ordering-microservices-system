package com.foodOdering.repository;

import com.foodOdering.model.User;

import java.util.HashMap;
import java.util.Map;
public class UserRepository {

    private final Map<Long, User> users = new HashMap<>();

    public void save(User user) {
        users.put(user.getUserId(), user);
    }

    public User findById(long userId) {
        return users.get(userId);
    }
}