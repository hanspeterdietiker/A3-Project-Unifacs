package com.restaurant.a3.RestaurantApi.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
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

    @Column(name = "text",nullable = false,length = 100)
    private String text;

    @Column(name = "creation_date",nullable = false)
    @CreatedDate
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name="date_update")
    private LocalDateTime dateUpdate;

    @CreatedBy
    @Column(name="name_create")
    private String nameCreate;

    @LastModifiedBy
    @Column(name="name_update")
    private String nameUpdate;

    @ManyToOne
    @JoinColumn(name = "id.user")
    private UserModel user;
}
