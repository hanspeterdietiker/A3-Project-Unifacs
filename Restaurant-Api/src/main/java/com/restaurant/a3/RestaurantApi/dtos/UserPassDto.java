package com.restaurant.a3.RestaurantApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserPassDto {

    @NotBlank
    @Size(min = 6, max = 6)
    private String password;

    @NotBlank
    @Size(min = 6, max = 6)
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 6)
    private String confirmPassword;
}
