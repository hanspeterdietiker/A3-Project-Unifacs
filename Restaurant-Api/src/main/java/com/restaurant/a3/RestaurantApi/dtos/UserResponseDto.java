package com.restaurant.a3.RestaurantApi.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserResponseDto {
    private Long id;
    private String name;
    private String username;
    private String role;
}
