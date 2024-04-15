package com.restaurant.a3.RestaurantApi.repositories;

import com.restaurant.a3.RestaurantApi.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
}
