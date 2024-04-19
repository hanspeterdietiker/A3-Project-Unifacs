package com.restaurant.a3.RestaurantApi.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AssessmentResponseDto {
    private int foodNote;
    private int serviceNote;
    private int ambientNote;
    private String nameCreate;
}
