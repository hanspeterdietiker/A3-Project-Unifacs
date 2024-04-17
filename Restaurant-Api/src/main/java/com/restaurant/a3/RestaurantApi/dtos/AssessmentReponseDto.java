package com.restaurant.a3.RestaurantApi.dtos;

import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AssessmentReponseDto {
    private int foodNote;
    private int serviceNote;
    private int ambientNote;
}
