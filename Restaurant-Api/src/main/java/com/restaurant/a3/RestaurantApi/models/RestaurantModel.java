package com.restaurant.a3.RestaurantApi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurant.a3.RestaurantApi.models.address.Address;
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

    @Column(name = "cep", nullable = false, length = 8)
    private String cep;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "district", nullable = false)
    private String district;

    @Getter
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<AssessmentModel> assessments = new ArrayList<>();

    @Getter
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private  List<CommentModel> comments = new ArrayList<>();

    public RestaurantModel (Long id, String name, String cep, String state, String city, String street, String district) {
        this.id = id;
        this.name = name;
        this.cep = cep;
        this.state = state;
        this.city = city;
        this.street = street;
        this.district = district;
    }

    }
