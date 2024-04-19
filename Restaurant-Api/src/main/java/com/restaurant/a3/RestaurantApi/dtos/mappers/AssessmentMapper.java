package com.restaurant.a3.RestaurantApi.dtos.mappers;

import com.restaurant.a3.RestaurantApi.dtos.AssessmentCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.AssessmentResponseDto;
import com.restaurant.a3.RestaurantApi.models.AssessmentModel;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class AssessmentMapper {
    public static AssessmentModel toAssessment(AssessmentCreateDto createDto) {
        return new ModelMapper().map(createDto, AssessmentModel.class);
    }

    public static AssessmentResponseDto toDto(AssessmentModel assessment) {
        return new ModelMapper().map(assessment, AssessmentResponseDto.class);
    }

    public static List<AssessmentResponseDto> toListDto(List<AssessmentModel> assessments) {
        return assessments.stream()
                .map(a -> toDto(a)).
                collect(Collectors.toList());

    }
}
