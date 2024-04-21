package com.restaurant.a3.RestaurantApi.repositories;

import com.restaurant.a3.RestaurantApi.dtos.UserCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.mappers.UserMapper;
import com.restaurant.a3.RestaurantApi.models.UserModel;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;


@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Deve retornar um usuário com sucesso")
    void findByUsernameSucess() {
        String username = "joana@email.com";
        UserCreateDto user = new UserCreateDto("Joanna",
                username, "123456");
        this.createUser(user);

        Optional<UserModel> foundedUser = this.userRepository.findByUsername(username);

        assertThat(foundedUser.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Não deve retornar um usuário com sucesso")
    void findByUsernameError() {
        String username = "joana@email.com";
        UserCreateDto user = new UserCreateDto("Joanna",
                username, "123456");

        Optional<UserModel> foundedUser = this.userRepository.findByUsername(username);

        assertThat(foundedUser.isEmpty()).isTrue();
    }

//    Metodo para criar um usário no h2
    private UserModel createUser(UserCreateDto user) {
    var newUser = UserMapper.toUser(user);
    this.entityManager.persist(newUser);

    return newUser;
    }

}