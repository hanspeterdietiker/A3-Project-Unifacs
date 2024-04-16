package com.restaurant.a3.RestaurantApi.controllers;

import com.restaurant.a3.RestaurantApi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/restaurant/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
}
