package com.restaurant.a3.RestaurantApi.repositories;

import com.restaurant.a3.RestaurantApi.models.AssessmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<AssessmentModel,Long> {
}
