package com.restaurant.a3.RestaurantApi.dtos.mappers;

import com.restaurant.a3.RestaurantApi.dtos.UserCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.UserResponseDto;
import com.restaurant.a3.RestaurantApi.models.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;


//Classe para transformar uma classe em dto
public class UserMapper {

    public static User toUser(UserCreateDto userCreateDto) {
        return new ModelMapper().map(userCreateDto, User.class);
    }

    public static UserResponseDto toDto(User user) {
//        Logica para tirar o 'ROLE_' do enum
        String role = user.getRole().name().substring("ROLE_".length());

        PropertyMap<User, UserResponseDto> props = new PropertyMap<User, UserResponseDto>() {
            @Override
            protected void configure() {
                map( ).setRole(role);
            }
        };

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);

        return mapper.map(user, UserResponseDto.class);
    }

    public static List<UserResponseDto> toListDto(List<User> users) {
       return users.stream()
                .map(u -> toDto(u)).
                collect(Collectors.toList());

    }
}
