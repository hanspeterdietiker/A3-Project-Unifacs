package com.restaurant.a3.RestaurantApi.controllers;

import com.restaurant.a3.RestaurantApi.Jwt.JwtUserDetails;
import com.restaurant.a3.RestaurantApi.dtos.*;
import com.restaurant.a3.RestaurantApi.dtos.mappers.AssessmentMapper;
import com.restaurant.a3.RestaurantApi.dtos.mappers.CommentMapper;
import com.restaurant.a3.RestaurantApi.dtos.mappers.RestaurantMapper;
import com.restaurant.a3.RestaurantApi.exceptions.ErrorMessage;
import com.restaurant.a3.RestaurantApi.models.address.viacep.Viacep;
import com.restaurant.a3.RestaurantApi.services.AssessmentService;
import com.restaurant.a3.RestaurantApi.services.CommentService;
import com.restaurant.a3.RestaurantApi.services.RestaurantService;
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

@Tag(name = "Restaurantes", description = "Realiza as operações de busca, remoção, adição de restaurantes e os seus comentários e avaliações.")
@RestController
@RequestMapping("/api/v1/restaurant")
@CrossOrigin("*")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AssessmentService assessmentService;

    @Operation(summary = "Criação de um restaurante",
            description = "Recurso cria um restaurante, exige um bearer Token (acesso restrito a admins)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestaurantResponseDto> createRestaurant(@RequestBody @Valid RestaurantCreateDto restaurant) {
        var r = RestaurantMapper.torestaurant(restaurant);
        r = restaurantService.addressRegister(r);
        return ResponseEntity.status(201).body(RestaurantMapper.toDto(restaurantService.saveRestaurant(r)));
    }

    @Operation(summary = "Criação de um comentário",
            description = "Recurso cria um comentário, exige um bearer Token (acesso restrito a usuários)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Comentário criado com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/{id}/comment")
    @PreAuthorize("hasRole('DEFAULT')")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody @Valid CommentCreateDto comment,
                                                            @AuthenticationPrincipal JwtUserDetails userDetails,
                                                            @PathVariable Long id) {
        var r = restaurantService.findById(id);

        var c = CommentMapper.toComment(comment);
        c.setUser(userService.findById(userDetails.getId()));
        c.setRestaurant(r);
        commentService.saveComment(c);

        return ResponseEntity.status(201).body(CommentMapper.toDto(c));
    }

    @Operation(summary = "Criação de uma avaliação",
            description = "Recurso cria uma avaliação, exige um bearer Token (acesso restrito a usuários)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/{id}/assessment")
    @PreAuthorize("hasRole('DEFAULT')")
    public ResponseEntity<AssessmentResponseDto> createAssessment(@RequestBody @Valid AssessmentCreateDto assessment,
                                                               @AuthenticationPrincipal JwtUserDetails userDetails,
                                                               @PathVariable Long id) {
        var r = restaurantService.findById(id);

        var a = AssessmentMapper.toAssessment(assessment);
        a.setUser(userService.findById(userDetails.getId()));
        a.setRestaurant(r);
        assessmentService.save(a);

        return ResponseEntity.status(201).body(AssessmentMapper.toDto(a));
    }

    @Operation(summary = "Localização de todos restaurantes",
            description = "Recurso exige a utilização de um bearer Token (acesso restrito a admin e usuário)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Restaurantes retornados com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DEFAULT')")
    public ResponseEntity<List<RestaurantResponseDto>> getRestaurants() {
        return  ResponseEntity.ok(RestaurantMapper.toListDto(restaurantService.getAll()));
    }

    @Operation(summary = "Localização de um restaurante por id",
            description = "Recurso exige a utilização de um bearer Token (acesso restrito a usuários padrões e admins)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Restaurante retornado com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEFAULT')")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@PathVariable Long id) {
        var restaurant = restaurantService.findById(id);
        return ResponseEntity.ok(RestaurantMapper.toDto(restaurant));
    }

    @Operation(summary = "Remoção de um restaurante",
            description = "Recurso que remove um restaurante por id, " +
                    "exige bearer token (acesso restrito a admins).",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Restaurante deletado com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRestaurantById(@PathVariable Long id) {
        restaurantService.removeRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
