package com.restaurant.a3.RestaurantApi.repositories;

import com.restaurant.a3.RestaurantApi.dtos.AssessmentCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.CommentCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.RestaurantCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.mappers.AssessmentMapper;
import com.restaurant.a3.RestaurantApi.dtos.mappers.CommentMapper;
import com.restaurant.a3.RestaurantApi.dtos.mappers.RestaurantMapper;
import com.restaurant.a3.RestaurantApi.models.AssessmentModel;
import com.restaurant.a3.RestaurantApi.models.CommentModel;
import com.restaurant.a3.RestaurantApi.models.RestaurantModel;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    EntityManager entityManager;


    private RestaurantModel createRestaurant() {
        RestaurantModel restaurant = new RestaurantModel(1L, "RestaurantTest", "12345678",
                "stateTest", "cityTest", "streetTest", "districtTest");
        return  this.entityManager.merge(restaurant);
    }

    private CommentModel createComment(CommentCreateDto c, RestaurantModel r) {
        var newComment = CommentMapper.toComment(c);
        newComment.setRestaurant(r);
        newComment.setCreationDate(LocalDateTime.now());
        newComment.setDateUpdate(LocalDateTime.now());
        this.entityManager.merge(newComment);

        return newComment;
    }

    private AssessmentModel createAssessment(AssessmentCreateDto a, RestaurantModel r) {
        var newAssessment = AssessmentMapper.toAssessment(a);
        newAssessment.setRestaurant(r);
        this.entityManager.merge(newAssessment);

        return newAssessment;
    }


}