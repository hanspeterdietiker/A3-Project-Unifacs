package com.restaurant.a3.RestaurantApi.IT;

import com.restaurant.a3.RestaurantApi.dtos.CommentResponseDto;
import com.restaurant.a3.RestaurantApi.exceptions.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/comments/comments-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/comments/comments-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CommentIT {

    @Autowired
    WebTestClient testClient;

    //	 Regra para criação de metodos referentes a testes:  motivo do teste_O que será testado_O que sera retornado


    @Test
    public void getComments_searchCommentsByIdForUser_ReturnStatus200() {
//        Um usuário buscando seus próprios comentários
        List<CommentResponseDto> response = testClient.get()
                .uri("/api/v1/restaurant/comment/details-user/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(2);

//        Um admin buscando os comentários de um usuário
        response = testClient.get()
                .uri("/api/v1/restaurant/comment/details-user/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(2);

    }

    @Test
    public void getComments_searchCommentsInvalidId_ReturnStatus404() {
//        Um admin buscando os comentários de um usuário com o id inexistente
        ErrorMessage response = testClient.get()
                .uri("/api/v1/restaurant/comment/details-user/210")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    public void getComments_searchCommentsInvalidAuthentication_ReturnStatus403() {
//        Um usuário buscando os comentários de outro usuário com
        ErrorMessage response = testClient.get()
                .uri("/api/v1/restaurant/comment/details-user/101")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(403);
    }
}
