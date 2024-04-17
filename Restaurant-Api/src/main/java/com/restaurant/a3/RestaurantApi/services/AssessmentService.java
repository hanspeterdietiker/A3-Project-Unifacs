package com.restaurant.a3.RestaurantApi.services;

import com.restaurant.a3.RestaurantApi.models.AssessmentModel;
import com.restaurant.a3.RestaurantApi.repositories.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    public AssessmentModel save(AssessmentModel a) {
        return assessmentRepository.save(a);
    }
}
