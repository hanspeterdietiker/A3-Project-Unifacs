package com.restaurant.a3.RestaurantApi.dtos.mappers;

import com.restaurant.a3.RestaurantApi.dtos.RestaurantCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.RestaurantResponseDto;
import com.restaurant.a3.RestaurantApi.models.RestaurantModel;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


//Classe para transformar uma classe em dto
public class RestaurantMapper {

    public static RestaurantModel torestaurant(RestaurantCreateDto restaurantCreateDto) {
        return new ModelMapper().map(restaurantCreateDto, RestaurantModel.class);
    }

    public static RestaurantResponseDto toDto(RestaurantModel restaurant) {
        return new ModelMapper().map(restaurant, RestaurantResponseDto.class);
    }

    public static List<RestaurantResponseDto> toListDto(List<RestaurantModel> restaurants) {
       return restaurants.stream()
                .map(r -> toDto(r)).
                collect(Collectors.toList());

    }
}
