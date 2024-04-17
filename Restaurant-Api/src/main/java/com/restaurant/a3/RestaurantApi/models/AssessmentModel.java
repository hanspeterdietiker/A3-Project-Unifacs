package com.restaurant.a3.RestaurantApi.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "app_AssessmentUser")
public class AssessmentModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_note", nullable = false, length = 5)
    private int foodNote;

    @Column(name = "service_note",nullable = false,length = 5)
    private int serviceNote;

    @Column(name = "ambient_note",nullable = false,length = 5)
    private int ambientNote;

    @ManyToOne
    @JoinColumn(name = "id.user")
    private UserModel user;
}