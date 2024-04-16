package com.restaurant.a3.RestaurantApi.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "app_users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;
    @Column(name = "password", nullable = false, length = 200)
    private String password;

//    Anotação para registrar o enum como string na base de dados
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false,  length = 25)
    private Role role = Role.ROLE_DEFAULT;


    public enum Role {
        ROLE_DEFAULT,
        ROLE_ADMIN
    }
}
