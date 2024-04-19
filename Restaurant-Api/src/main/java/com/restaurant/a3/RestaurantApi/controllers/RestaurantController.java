package com.restaurant.a3.RestaurantApi.controllers;

import com.restaurant.a3.RestaurantApi.Jwt.JwtUserDetails;
import com.restaurant.a3.RestaurantApi.dtos.*;
import com.restaurant.a3.RestaurantApi.dtos.mappers.AssessmentMapper;
import com.restaurant.a3.RestaurantApi.dtos.mappers.CommentMapper;
import com.restaurant.a3.RestaurantApi.dtos.mappers.RestaurantMapper;
import com.restaurant.a3.RestaurantApi.models.address.viacep.Viacep;
import com.restaurant.a3.RestaurantApi.services.AssessmentService;
import com.restaurant.a3.RestaurantApi.services.CommentService;
import com.restaurant.a3.RestaurantApi.services.RestaurantService;
import com.restaurant.a3.RestaurantApi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AssessmentService assessmentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestaurantResponseDto> createRestaurant(@RequestBody @Valid RestaurantCreateDto restaurant) {
        var r = RestaurantMapper.torestaurant(restaurant);
        r = restaurantService.addressRegister(r);
        return ResponseEntity.status(201).body(RestaurantMapper.toDto(restaurantService.saveRestaurant(r)));
    }

    @PostMapping("/{id}/comment")
    @PreAuthorize("hasRole('DEFAULT')")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody @Valid CommentCreateDto comment,
                                                            @AuthenticationPrincipal JwtUserDetails userDetails,
                                                            @PathVariable Long id) {
        var r = restaurantService.findById(id);

        var c = CommentMapper.toComment(comment);
        c.setUser(userService.findById(userDetails.getId()));
        c.setRestaurant(r);
        commentService.saveComment(c);

        return ResponseEntity.status(201).body(CommentMapper.toDto(c));
    }

    @PostMapping("/{id}/assessment")
    @PreAuthorize("hasRole('DEFAULT')")
    public ResponseEntity<AssessmentResponseDto> createAssessment(@RequestBody @Valid AssessmentCreateDto assessment,
                                                               @AuthenticationPrincipal JwtUserDetails userDetails,
                                                               @PathVariable Long id) {
        var r = restaurantService.findById(id);

        var a = AssessmentMapper.toAssessment(assessment);
        a.setUser(userService.findById(userDetails.getId()));
        a.setRestaurant(r);
        assessmentService.save(a);

        return ResponseEntity.status(201).body(AssessmentMapper.toDto(a));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DEFAULT')")
    public ResponseEntity<List<RestaurantResponseDto>> getRestaurants() {
        return  ResponseEntity.ok(RestaurantMapper.toListDto(restaurantService.getAll()));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEFAULT')")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@PathVariable Long id) {
        var restaurant = restaurantService.findById(id);
        return ResponseEntity.ok(RestaurantMapper.toDto(restaurant));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRestaurantById(@PathVariable Long id) {
        restaurantService.removeRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
