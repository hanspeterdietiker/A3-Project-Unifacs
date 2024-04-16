package com.restaurant.a3.RestaurantApi.services;

import com.restaurant.a3.RestaurantApi.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
}
