package com.foodOdering.service;
import com.foodOdering.model.User;
import com.foodOdering.repository.UserRepository;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {

    private final UserRepository userRepository;
    private final AtomicLong idGenerator = new AtomicLong(1000);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long registerUser(String name, String email, String phone) {

        long id = idGenerator.incrementAndGet();
        User user = new User(id, name, email, phone);
        userRepository.save(user);

        System.out.println("User Registered!!! UserId: " + id);
        System.out.println(" ");
        return id;
    }

    public User getUser(long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        return user;
    }
}