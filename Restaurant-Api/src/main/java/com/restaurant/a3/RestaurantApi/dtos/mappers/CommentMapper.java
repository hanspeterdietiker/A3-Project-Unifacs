package com.restaurant.a3.RestaurantApi.dtos.mappers;

import com.restaurant.a3.RestaurantApi.dtos.CommentCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.CommentResponseDto;
import com.restaurant.a3.RestaurantApi.dtos.UserCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.UserResponseDto;
import com.restaurant.a3.RestaurantApi.models.CommentModel;
import com.restaurant.a3.RestaurantApi.models.UserModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {
    public static CommentModel toComment(CommentCreateDto createDto) {
        return new ModelMapper().map(createDto, CommentModel.class);
    }

    public static CommentResponseDto toDto(CommentModel coment) {
        return new ModelMapper().map(coment, CommentResponseDto.class);
    }

    public static List<CommentResponseDto> toListDto(List<CommentModel> comments) {
        return comments.stream()
                .map(c -> toDto(c)).
                collect(Collectors.toList());

    }
}
