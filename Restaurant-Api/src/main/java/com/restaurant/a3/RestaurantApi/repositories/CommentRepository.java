package com.restaurant.a3.RestaurantApi.repositories;

import com.restaurant.a3.RestaurantApi.models.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentModel, Long> {

    @Query("select c from CommentModel c where c.user.id = :userId")
    List<CommentModel> findCommentsByUser(@Param("userId") Long userId);

}
