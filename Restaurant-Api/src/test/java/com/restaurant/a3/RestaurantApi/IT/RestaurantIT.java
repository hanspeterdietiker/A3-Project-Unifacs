package com.restaurant.a3.RestaurantApi.IT;
import com.restaurant.a3.RestaurantApi.dtos.*;
import com.restaurant.a3.RestaurantApi.exceptions.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/restaurants/restaurant-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/restaurants/restaurant-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RestaurantIT {
    @Autowired
    WebTestClient testClient;

    @Test
    public void createRestaurant_WithAdminAuthentication_ReturnStatus201() {
//        Admin criando um restaurante
        RestaurantResponseDto response = testClient.post()
                .uri("/api/v1/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .bodyValue(new RestaurantCreateDto("RestaurantTest", "41940340"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(RestaurantResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("RestaurantTest");
    }

    @Test
    public void createRestaurant_WithInvalidAuthentication_ReturnStatus403() {
//        Usuário tentando criar um restaurante
        ErrorMessage response = testClient.post()
                .uri("/api/v1/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .bodyValue(new RestaurantCreateDto("RestaurantTest", "41940340"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(403);
    }

    @Test
    public  void createRestaurant_WithDataInvalid_ReturnStatus422() {
//        Admin criando um restaurante com um digito a mais no cep
        ErrorMessage response = testClient.post()
                .uri("/api/v1/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .bodyValue(new RestaurantCreateDto("RestaurantTest", "419403400"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(422);
    }

    @Test
    public void createCommentInRestaurant_WithUserAuthentication_ReturnStatus201() {
//        Usuário criando comentario no restaurante de id 20
        CommentResponseDto response = testClient.post()
                .uri("/api/v1/restaurant/20/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .bodyValue(new CommentCreateDto("CommentTest"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CommentResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getText()).isEqualTo("CommentTest");
    }

    @Test
    public  void createCommentInRestaurant_WithInvalidAuthentication_ReturnStatus403() {
//        Admin tentando registrar um comentário
        ErrorMessage response = testClient.post()
                .uri("/api/v1/restaurant/20/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .bodyValue(new CommentCreateDto("CommentTest"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(403);
    }

    @Test
    public void createAssessmentInRestaurant_WithUserAuthentication_ReturnStatus201() {
//        Usuário criando avaliação no restaurante de id 20
        AssessmentResponseDto response = testClient.post()
                .uri("/api/v1/restaurant/20/assessment")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .bodyValue(new AssessmentCreateDto(5, 5 , 3))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AssessmentResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getFoodNote()).isEqualTo(5);
    }

    @Test
    public  void createAssessmentInRestaurant_WithInvalidAuthentication_ReturnStatus403() {
//        Admin tentando registrar uma avaliação
        ErrorMessage response = testClient.post()
                .uri("/api/v1/restaurant/20/assessment")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .bodyValue(new AssessmentCreateDto(5, 5 , 3))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(403);
    }

    @Test
    public  void getAllRestaurants_WithValidAuthetication_ReturnStatus200() {
//        Admin e usuário buscando todos os restaurantes
        List<RestaurantResponseDto> response = testClient.get()
                .uri("/api/v1/restaurant")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RestaurantResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(3);

        response = testClient.get()
                .uri("/api/v1/restaurant")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RestaurantResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(3);
    }

    @Test
    public  void getRestaurantById_WithValidAuthentication_ReturnStatus200() {
        //        Admin e usuário buscando um restaurante por id
        RestaurantResponseDto response = testClient.get()
                .uri("/api/v1/restaurant/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(RestaurantResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();

        response = testClient.get()
                .uri("/api/v1/restaurant/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(RestaurantResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void getRestaurantsById_WithRestaurantNotAlreadyExistis_ReturnStatus404() {
        ErrorMessage response = testClient.get()
                .uri("/api/v1/restaurant/230")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    public  void deleteRestaurantById_WithValidationAuthorization_ReturnStatus200() {
        //Admin removendo um restaurante
        testClient.delete()
                .uri("/api/v1/restaurant/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isNoContent();

    }

    @Test
    public  void deleteRestaurantById_WithInvalidAuthorization_ReturnStatus403() {
//        Usuário tentando remover um restaurante

        ErrorMessage response = testClient.delete()
                .uri("/api/v1/restaurant/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(403);
    }
}
