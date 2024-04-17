package com.restaurant.a3.RestaurantApi.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "app_AssessmentUser")
@EntityListeners(AuditingEntityListener.class)
public class AssessmentModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_note", nullable = false, length = 1)
    private int foodNote;

    @Column(name = "service_note",nullable = false,length = 1)
    private int serviceNote;

    @Column(name = "ambient_note",nullable = false,length = 1)
    private int ambientNote;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @CreatedBy
    @Column(name="name_create")
    private String nameCreate;
}