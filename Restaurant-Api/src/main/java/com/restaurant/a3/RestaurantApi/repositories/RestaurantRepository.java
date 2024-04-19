package com.restaurant.a3.RestaurantApi.repositories;

import com.restaurant.a3.RestaurantApi.models.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantModel, Long> {
    @Query("SELECT r FROM RestaurantModel r LEFT JOIN FETCH r.comments WHERE r.id = :id")
    Optional<RestaurantModel> findByIdWithComments(@Param("id") Long id);
}
