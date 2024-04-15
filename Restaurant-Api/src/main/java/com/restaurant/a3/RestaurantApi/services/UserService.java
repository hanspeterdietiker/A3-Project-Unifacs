package com.restaurant.a3.RestaurantApi.services;

import com.restaurant.a3.RestaurantApi.Exceptions.EntityNotFoundException;
import com.restaurant.a3.RestaurantApi.models.UserModel;
import com.restaurant.a3.RestaurantApi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserModel> findAll() {
        return  userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserModel findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário de ID: '%s', não encontrado!", id))
        );
    }

    @Transactional
    public UserModel saveUser(UserModel user) {
        return userRepository.save(user);

    }
}
