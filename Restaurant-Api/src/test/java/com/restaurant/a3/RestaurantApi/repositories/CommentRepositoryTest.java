package com.restaurant.a3.RestaurantApi.repositories;

import com.restaurant.a3.RestaurantApi.dtos.CommentCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.UserCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.mappers.CommentMapper;
import com.restaurant.a3.RestaurantApi.dtos.mappers.UserMapper;
import com.restaurant.a3.RestaurantApi.models.CommentModel;
import com.restaurant.a3.RestaurantApi.models.RestaurantModel;
import com.restaurant.a3.RestaurantApi.models.UserModel;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;


@DataJpaTest
@ActiveProfiles("test")
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Deve retornar os comentários de um usuário com sucesso")
    void findCommentsByUserSucess() {
        String username = "joana@email.com";
        UserCreateDto user = new UserCreateDto("Joanna",
                username, "123456");
        var u = this.createUser(user);

        CommentCreateDto comment1 = new CommentCreateDto("Restaurante bem aromatizado!");
        CommentCreateDto comment2 = new CommentCreateDto("Restaurante bem aromatizado!");
        CommentModel testComment = this.createComment(comment1, u);
        this.createComment(comment2, u);

        List<CommentModel> comments = commentRepository.findCommentsByUser(u.getId());
        assertThat(comments.isEmpty()).isFalse();
        assertThat(comments.size()).isEqualTo(2);
        assertThat(comments.get(0).getText()).isEqualTo(testComment.getText());
    }

    @Test
    @DisplayName("Não deve retornar os comentários de um usuário com sucesso")
    void findCommentsByUserError() {
        String username = "joana@email.com";
        UserCreateDto user = new UserCreateDto("Joanna",
                username, "123456");

        UserCreateDto user2 = new UserCreateDto("Luis",
                "luis@email.com", "123456");
        var u = this.createUser(user);

        CommentCreateDto comment1 = new CommentCreateDto("Restaurante bem aromatizado!");
        CommentCreateDto comment2 = new CommentCreateDto("Restaurante bem aromatizado!");

//        Testando o metodo com o usuário persistido na base de dados
        List<CommentModel> comments = commentRepository.findCommentsByUser(u.getId());
        assertThat(comments.isEmpty()).isTrue();


//        Testando o metodo com o usuário não persistido na base de dados
        var u2 = UserMapper.toUser(user2);
        comments = commentRepository.findCommentsByUser(u2.getId());
        assertThat(comments.isEmpty()).isTrue();

    }

//    Metodo para criar um usário no h2
    private UserModel createUser(UserCreateDto user) {
        var newUser = UserMapper.toUser(user);
        this.entityManager.persist(newUser);

        return newUser;
    }

    private CommentModel createComment(CommentCreateDto comment, UserModel user) {
        var newComment = CommentMapper.toComment(comment);
        newComment.setUser(user);
        newComment.setRestaurant(createRestaurant());
        newComment.setCreationDate(LocalDateTime.now());
        newComment.setDateUpdate(LocalDateTime.now());
        this.entityManager.persist(newComment);

        return newComment;
    }

    private RestaurantModel createRestaurant() {
        RestaurantModel restaurant = new RestaurantModel(1L, "RestaurantTest", "12345678",
                "stateTest", "cityTest", "streetTest", "districtTest");
       return  this.entityManager.merge(restaurant);
    }

}