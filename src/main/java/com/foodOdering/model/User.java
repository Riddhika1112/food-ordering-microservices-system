package com.foodOdering.model;

public class User {
    private final long userId;
    private final String name;
    private final String email;
    private final String phoneNumber;

    public User(long userId, String name, String email, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
