package com.restaurant.a3.RestaurantApi.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AssessmentCreateDto {
    @Min(1)
    @Max(5)
    @NotBlank
    private int foodNote;
    @Min(1)
    @Max(5)
    @NotBlank
    private int serviceNote;
    @Min(1)
    @Max(5)
    @NotBlank
    private int ambientNote;
}
