package com.restaurant.a3.RestaurantApi.controllers;

import com.restaurant.a3.RestaurantApi.dtos.UserCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.UserPassDto;
import com.restaurant.a3.RestaurantApi.dtos.UserResponseDto;
import com.restaurant.a3.RestaurantApi.dtos.mappers.UserMapper;
import com.restaurant.a3.RestaurantApi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(UserMapper.toListDto(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(UserMapper.toDto(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        var user = userService.saveUser(UserMapper.toUser(userCreateDto));
        return ResponseEntity.status(201).body(UserMapper.toDto(user));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody @Valid UserPassDto userPassDto) {
            userService.updatePassword(id,
                    userPassDto.getPassword(),
                    userPassDto.getNewPassword(),
                    userPassDto.getConfirmPassword());

            return ResponseEntity.noContent().build();
    }
}
