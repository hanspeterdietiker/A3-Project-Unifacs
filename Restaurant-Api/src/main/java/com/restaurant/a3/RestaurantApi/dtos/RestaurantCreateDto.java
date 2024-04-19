package com.restaurant.a3.RestaurantApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RestaurantCreateDto {
    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 8, max = 8)
    private String cep;

}
