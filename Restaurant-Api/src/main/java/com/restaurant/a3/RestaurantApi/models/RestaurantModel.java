package com.restaurant.a3.RestaurantApi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
    // O metodo cascade caso um restaurante seja excluido
    // todas as avaliaçoes e comentarios também serao excluidos.
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "app_Restaurant")
@EntityListeners(AuditingEntityListener.class)
public class RestaurantModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "restaurant_address", nullable = false, length = 255)
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant_assesment", cascade = CascadeType.ALL)
    private List<AssessmentModel> assessments= new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant_comment",cascade = CascadeType.ALL)
    private  List<CommentModel> comments= new ArrayList<>();
}
