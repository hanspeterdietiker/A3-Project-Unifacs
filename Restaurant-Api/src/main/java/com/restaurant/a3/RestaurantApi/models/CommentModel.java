package com.restaurant.a3.RestaurantApi.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "app_CommentUser")
public class CommentModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text",nullable = false,length = 50)
    private String text;

    @Column(name = "creation_date",nullable = false)
    private ZonedDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private UserModel user;
}
