package com.restaurant.a3.RestaurantApi.controllers;

import com.restaurant.a3.RestaurantApi.Jwt.JwtUserDetails;
import com.restaurant.a3.RestaurantApi.dtos.CommentCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.CommentResponseDto;
import com.restaurant.a3.RestaurantApi.dtos.mappers.CommentMapper;
import com.restaurant.a3.RestaurantApi.services.CommentService;
import com.restaurant.a3.RestaurantApi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('DEFAULT')")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody @Valid CommentCreateDto comment,
                                                            @AuthenticationPrincipal JwtUserDetails userDetails) {
        var c = CommentMapper.toComment(comment);
        c.setUser(userService.findById(userDetails.getId()));
        commentService.saveComment(c);
        return ResponseEntity.status(201).body(CommentMapper.toDto(c));
    }

    @GetMapping("/details-user/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('DEFAULT') AND #id == authentication.principal.id)")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(CommentMapper.toListDto(commentService.getCommentsByUserId(id)));

    }
}
