package com.restaurant.a3.RestaurantApi.dtos;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentResponseDto {
    private Long id;
    private String text;
    private LocalDateTime creationDate;
    private String nameCreate;
}
