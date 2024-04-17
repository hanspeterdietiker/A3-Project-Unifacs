package com.restaurant.a3.RestaurantApi.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentCreateDto {
    @NotNull
    private String text;
}
