package com.restaurant.a3.RestaurantApi.services;

import com.restaurant.a3.RestaurantApi.exceptions.EmailUniqueViolationException;
import com.restaurant.a3.RestaurantApi.exceptions.EntityNotFoundException;
import com.restaurant.a3.RestaurantApi.models.UserModel;
import com.restaurant.a3.RestaurantApi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@DataJpaTest
@ActiveProfiles("test")
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar uma lista de usuários com sucesso")
    void findAllSucess() {
        List<UserModel> users = new ArrayList<>();
        UserModel u1 = new UserModel();
        u1.setId(1L);
        u1.setName("Luis");

        UserModel u2 = new UserModel();
        u2.setId(2L);
        u2.setName("Lucas");

        users.add(u1);
        users.add(u2);

        when(userRepository.findAll()).thenReturn(users);

        List<UserModel> getUsers = userService.findAll();

        assertThat(getUsers.isEmpty()).isFalse();
        assertThat(getUsers.size()).isEqualTo(2);
        assertThat(getUsers.get(0)).isEqualTo(u1);
        assertThat(getUsers.get(1)).isEqualTo(u2);
        verify(userRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Deve retornar um usuário por id com sucesso")
    void findByIdSucess() {
        UserModel u = new UserModel();
        u.setName("userTest");

        when(userRepository.findById(1L)).thenReturn(Optional.of(u));

        UserModel getUserById = userService.findById(1L);

        assertThat(getUserById).isNotNull();
        assertThat(getUserById.getName()).isEqualTo("userTest");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao buscar um usuário por id inexistente")
    void findByIdIsNotFound() {
        when(userRepository.findById(1L)).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> userService.findById(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Deve salvar um usuário com sucesso no DB")
    void saveUserSucess() {
        UserModel u = new UserModel();
        u.setName("userTest");

        when(userRepository.save(u)).thenReturn(u);

        UserModel getUser = userService.saveUser(u);

        assertThat(getUser).isNotNull();
        assertThat(getUser.getName()).isEqualTo("userTest");
        verify(userRepository, times(1)).save(u);
    }

    @Test
    @DisplayName("Deve retornar uma exceção ao tentar salvar um usuário com o e-mail ja cadastrado")
    void saveUserDataIntegrityViolation() {
        UserModel u = new UserModel();
        u.setUsername("test@email.com");

        when(userRepository.save(u)).thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> userService.saveUser(u))
                .isInstanceOf(EmailUniqueViolationException.class)
                .hasMessageContaining(String.format("Email: '%s' já cadastrado!", u.getUsername()));
    }

    @Test
    @DisplayName("Deve retornar um usuário pelo seu e-mail com sucesso")
    void findByUsernameSucess() {
        UserModel u = new UserModel();
        u.setUsername("test@email.com");

        when(userRepository.findByUsername("test@email.com")).thenReturn(Optional.of(u));

        UserModel getUser = userService.findByUsername("test@email.com");

        assertThat(getUser).isNotNull();
        assertThat(getUser.getUsername()).isEqualTo("test@email.com");
        verify(userRepository, times(1)).findByUsername("test@email.com");
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao buscar um usuário com e-mail inexistente")
    void findByUsernameIsNotFound() {
        when(userRepository.findByUsername("test@email.com")).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> userService.findByUsername("test@email.com"))
                .isInstanceOf(EntityNotFoundException.class);
    }
}