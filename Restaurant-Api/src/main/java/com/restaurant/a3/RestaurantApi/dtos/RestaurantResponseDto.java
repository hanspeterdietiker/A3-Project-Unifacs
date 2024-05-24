package com.restaurant.a3.RestaurantApi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.restaurant.a3.RestaurantApi.models.address.Address;
import jakarta.persistence.Column;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantResponseDto {
    private Long id;
    private String name;
    private String state;
    private String city;
    private String street;
    private String district;
    private List<CommentResponseDto> comments;
    private List<AssessmentResponseDto> assessments;
}
