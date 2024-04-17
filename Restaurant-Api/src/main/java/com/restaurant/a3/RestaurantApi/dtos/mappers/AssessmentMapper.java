package com.restaurant.a3.RestaurantApi.dtos.mappers;

import com.restaurant.a3.RestaurantApi.dtos.AssessmentCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.AssessmentReponseDto;
import com.restaurant.a3.RestaurantApi.dtos.CommentCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.CommentResponseDto;
import com.restaurant.a3.RestaurantApi.models.AssessmentModel;
import com.restaurant.a3.RestaurantApi.models.CommentModel;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class AssessmentMapper {
    public static AssessmentModel toAssessment(AssessmentCreateDto createDto) {
        return new ModelMapper().map(createDto, AssessmentModel.class);
    }

    public static AssessmentReponseDto toDto(AssessmentModel assessment) {
        return new ModelMapper().map(assessment, AssessmentReponseDto.class);
    }

    public static List<AssessmentReponseDto> toListDto(List<AssessmentModel> assessments) {
        return assessments.stream()
                .map(a -> toDto(a)).
                collect(Collectors.toList());

    }
}
