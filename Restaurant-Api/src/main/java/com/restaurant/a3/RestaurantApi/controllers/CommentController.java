package com.restaurant.a3.RestaurantApi.controllers;

import com.restaurant.a3.RestaurantApi.Jwt.JwtUserDetails;
import com.restaurant.a3.RestaurantApi.dtos.CommentCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.CommentResponseDto;
import com.restaurant.a3.RestaurantApi.dtos.UserResponseDto;
import com.restaurant.a3.RestaurantApi.dtos.mappers.CommentMapper;
import com.restaurant.a3.RestaurantApi.exceptions.ErrorMessage;
import com.restaurant.a3.RestaurantApi.services.CommentService;
import com.restaurant.a3.RestaurantApi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comentários", description = "Realiza as operações de busca dos comentários de um usuário por id e de remoção de comentários em um restaurante.")
@RestController
@RequestMapping("/api/v1/restaurant/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Localização do comentários de um usuário por id",
            description = "Recurso exige a utilização de um bearer Token (acesso restrito a usuários padrões e admins)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/details-user/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('DEFAULT') AND #id == authentication.principal.id)")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(CommentMapper.toListDto(commentService.getCommentsByUserId(id)));

    }


}
