package com.restaurant.a3.RestaurantApi.controllers;

import com.restaurant.a3.RestaurantApi.Jwt.JwtUserDetails;
import com.restaurant.a3.RestaurantApi.dtos.AssessmentCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.AssessmentReponseDto;
import com.restaurant.a3.RestaurantApi.dtos.mappers.AssessmentMapper;
import com.restaurant.a3.RestaurantApi.models.AssessmentModel;
import com.restaurant.a3.RestaurantApi.services.AssessmentService;
import com.restaurant.a3.RestaurantApi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurant/assessment")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('DEFAULT')")
    public ResponseEntity<AssessmentReponseDto> createAssessment(@RequestBody @Valid AssessmentCreateDto assessmentCreateDto,
                                                                 @AuthenticationPrincipal JwtUserDetails userDetails) {
        var a = AssessmentMapper.toAssessment(assessmentCreateDto);
        a.setUser(userService.findById(userDetails.getId()));
        assessmentService.save(a);
        return ResponseEntity.status(201).body(AssessmentMapper.toDto(a));
    }
}
