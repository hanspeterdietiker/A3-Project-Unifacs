package com.restaurant.a3.RestaurantApi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "app_users")
public class UserModel implements Serializable {

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

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<CommentModel> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<AssessmentModel> assesments = new ArrayList<>();


    public enum Role {
        ROLE_DEFAULT,
        ROLE_ADMIN
    }
}
