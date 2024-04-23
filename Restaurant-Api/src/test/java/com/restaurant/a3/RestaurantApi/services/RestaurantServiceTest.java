package com.restaurant.a3.RestaurantApi.services;

import com.restaurant.a3.RestaurantApi.exceptions.EntityNotFoundException;
import com.restaurant.a3.RestaurantApi.models.RestaurantModel;
import com.restaurant.a3.RestaurantApi.repositories.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@DataJpaTest
@ActiveProfiles("test")
class RestaurantServiceTest {

    @InjectMocks
    RestaurantService restaurantService;

    @Mock
    RestaurantRepository restaurantRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve salvar e retornar um restaurante com sucesso")
    void saveRestaurantSucess() {
        RestaurantModel r = new RestaurantModel();
        r.setName("restaurantTest");

        when(restaurantRepository.save(r)).thenReturn(r);

        RestaurantModel savedRestaurant = restaurantService.saveRestaurant(r);

        assertThat(r).isNotNull();
        assertThat(r.getName()).isEqualTo("restaurantTest");
        verify(restaurantRepository, times(1)).save(r);
    }

    @Test
    @DisplayName("Deve retornar uma lista de restaurantes com sucessp")
    void getAllSucess() {
        List<RestaurantModel> restaurants = new ArrayList<>();
        RestaurantModel r = new RestaurantModel();
        restaurants.add(r);
        RestaurantModel r2 = new RestaurantModel();
        restaurants.add(r2);

        when(restaurantRepository.findAll()).thenReturn(restaurants);

        List<RestaurantModel> getRestaurants = restaurantService.getAll();

        assertThat(getRestaurants.isEmpty()).isFalse();
        assertThat(getRestaurants.size()).isEqualTo(2);
        assertThat(getRestaurants.get(0)).isEqualTo(r);
        assertThat(getRestaurants.get(1)).isEqualTo(r2);
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar um usuário, por id, com sucesso")
    void findByIdSucess() {
        RestaurantModel r = new RestaurantModel();
        r.setName("restaurantTest");

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(r));

        RestaurantModel getRestaurantById = restaurantService.findById(1L);

        assertThat(getRestaurantById).isNotNull();
        assertThat(getRestaurantById.getName()).isEqualTo("restaurantTest");
        verify(restaurantRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao buscar um usuário, por id, inexistente")
    void findByIdIsNotFound() {

        when(restaurantRepository.findById(1L)).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> restaurantService.findById(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }
}