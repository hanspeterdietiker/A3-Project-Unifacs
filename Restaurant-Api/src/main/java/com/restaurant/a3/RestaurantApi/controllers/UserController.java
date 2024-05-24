package com.restaurant.a3.RestaurantApi.controllers;

import com.restaurant.a3.RestaurantApi.dtos.UserCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.UserPassDto;
import com.restaurant.a3.RestaurantApi.dtos.UserResponseDto;
import com.restaurant.a3.RestaurantApi.dtos.mappers.UserMapper;
import com.restaurant.a3.RestaurantApi.exceptions.ErrorMessage;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "Realiza todas as operações de leitura, adição e edição do usuário.")
@RestController
@RequestMapping("/api/v1/restaurant/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Localização de todos usuários",
            description = "Recurso exige a utilização de um bearer Token (acesso restrito a admin)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                        @ApiResponse(responseCode = "200", description = "Usuários retornados com sucesso!",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                        @ApiResponse(responseCode = "403", description = "Acesso negado!",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(UserMapper.toListDto(userService.findAll()));
    }

    @Operation(summary = "Localização de um usuário por id",
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
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR ( hasRole('DEFAULT') AND #id == authentication.principal.id)")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(UserMapper.toDto(userService.findById(id)));
    }

    @Operation(summary = "Criação de um usuário",
                description = "Recurso cria um usuário, não exige um bearer Token",
                security = @SecurityRequirement(name = "security"),
                responses = {
                            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                            @ApiResponse(responseCode = "409", description = "Email já cadastrado no sistema!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                            @ApiResponse(responseCode = "422", description = "Campos inválidos!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
                })
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        var user = userService.saveUser(UserMapper.toUser(userCreateDto));
        return ResponseEntity.status(201).body(UserMapper.toDto(user));
    }

    @Operation(summary = "Atualização da senha de um usuário",
                description = "Recurso que atualiza o próprio usuário, " +
                        "exige bearer token e apenas o próprio usuário, seja ele admin ou padrão, pode atualizar seus dados.",
                security = @SecurityRequirement(name = "security"),
                responses = {
                            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                            @ApiResponse(responseCode = "403", description = "Acesso negado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                            @ApiResponse(responseCode = "422", description = "Campos inválidos!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                            @ApiResponse(responseCode = "400", description = "Senhas incompativeis!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
                })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEFAULT') AND (#id == authentication.principal.id)")
    public  ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody @Valid UserPassDto userPassDto) {
            userService.updatePassword(id,
                    userPassDto.getPassword(),
                    userPassDto.getNewPassword(),
                    userPassDto.getConfirmPassword());

            return ResponseEntity.noContent().build();
    }
}
