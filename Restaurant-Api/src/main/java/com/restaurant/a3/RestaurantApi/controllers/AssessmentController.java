package com.restaurant.a3.RestaurantApi.controllers;

import com.restaurant.a3.RestaurantApi.services.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurant/assessment")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;
}
