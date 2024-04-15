package com.restaurant.a3.RestaurantApi.services;

import com.restaurant.a3.RestaurantApi.models.UserModel;
import com.restaurant.a3.RestaurantApi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserModel saveUser(UserModel user) {
        return userRepository.save(user);

    }
}
