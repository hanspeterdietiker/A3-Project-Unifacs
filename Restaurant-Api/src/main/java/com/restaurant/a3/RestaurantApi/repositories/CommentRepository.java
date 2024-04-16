package com.restaurant.a3.RestaurantApi.repositories;

import com.restaurant.a3.RestaurantApi.models.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentModel, Long> {
}
