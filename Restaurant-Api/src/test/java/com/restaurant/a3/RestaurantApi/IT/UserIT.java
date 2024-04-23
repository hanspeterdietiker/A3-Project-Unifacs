package com.restaurant.a3.RestaurantApi.IT;
import com.restaurant.a3.RestaurantApi.dtos.UserCreateDto;
import com.restaurant.a3.RestaurantApi.dtos.UserPassDto;
import com.restaurant.a3.RestaurantApi.dtos.UserResponseDto;
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
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

    @Autowired
    WebTestClient testClient;

    //	 Regra para criação de metodos referentes a testes:  motivo do teste_O que será testado_O que sera retornado

    @Test
    public  void createUser_WithDataValidation_ReturnStatus201() {
        UserResponseDto response = testClient.post()
                .uri("/api/v1/restaurant/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("test", "test@email.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("test");
        assertThat(response.getUsername()).isEqualTo("test@email.com");
        assertThat(response.getRole()).isEqualTo("DEFAULT");

    }

    @Test
    public  void createUser_WithUsernameAndPasswordInvalidation_ReturnStatus422() {
//        Tentando criar um usuário cm uma senha inválida
        ErrorMessage response = testClient.post()
                .uri("/api/v1/restaurant/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("test", "test@email.com", "12345"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(422);

//        Tentando criar um usuário com o email inválido
        response = testClient.post()
                .uri("/api/v1/restaurant/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("test", "test@ema", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(422);

        response = testClient.post()
                .uri("/api/v1/restaurant/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("test", "", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(422);

    }

    @Test
    public void createUser_WithUsernameAlreadyExists_ReturnStatus409() {
        ErrorMessage response = testClient.post()
                .uri("/api/v1/restaurant/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("test", "luis@email.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(409);
    }

    @Test
    public void getAllUsers_WithValidAuthentication_ReturnStatus200() {
//        Admin buscando todos os usuários
        List<UserResponseDto> response = testClient.get()
                .uri("/api/v1/restaurant/users")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(4);
    }

    @Test
    public  void getAllUsers_WithInvalidAuthentication_ReturnStatus403() {
//        Usuário tentando buscar todos os usuários
        ErrorMessage response = testClient.get()
                .uri("/api/v1/restaurant/users")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(403);

    }

    @Test
    public void getUserById_WithValidAuthentication_ReturnStatus200() {
//        Admin buscando um usuário por id
        UserResponseDto response = testClient.get()
                .uri("/api/v1/restaurant/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(100);

//       Um usuário buscando seus próprios dados por id
         response = testClient.get()
                .uri("/api/v1/restaurant/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(100);
    }

    @Test
    public void getUserById_NotFoundedUser_returnStatus404() {
//        Admin buscando um usuário por id inexistente
        ErrorMessage response = testClient.get()
                .uri("/api/v1/restaurant/users/200")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    public void getUserById_WithInvalidAuthentication_returnStatus403() {
//        Usuário tentando buscar um outro usuário por id
        ErrorMessage response = testClient.get()
                .uri("/api/v1/restaurant/users/101")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(403);
    }

    @Test
    public  void putUserById_WithDataValidation_ReturnStatus200() {
        //Admin modificando sua senha
        testClient.put()
                .uri("/api/v1/restaurant/users/103")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassDto("123456", "654321", "654321"))
                .exchange()
                .expectStatus().isNoContent();

        //Usuário modificando sua senha
        testClient.put()
                .uri("/api/v1/restaurant/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassDto("123456", "654321", "654321"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void putUser_WithInvalidAuthentication_ReturnStatus403() {
//		Usuário tentando alterar senha de outro usuário
        ErrorMessage responseBody = testClient.put()
                .uri("api/v1/restaurant/users/101")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassDto("123456", "654322", "654321"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(403);

//		Admin tentando alterar senha de outro usuário
        responseBody = testClient.put()
                .uri("api/v1/restaurant/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassDto("123456", "654322", "654321"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(403);

    }

    @Test
    public void userPut_WithInvalidPass_ReturnStatus400() {
//		Usuario inserindo senha de confirmação errada
        ErrorMessage responseBody = testClient.put()
                .uri("api/v1/restaurant/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassDto("123456", "654322", "654321"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(400);

//		Usuario inserindo senha atual errada
        responseBody = testClient.put()
                .uri("api/v1/restaurant/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassDto("123455", "654321", "654321"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(400);


    }

    @Test
    public void userPut_WithDataInvalid_ReturnStatus422() {
//		Usuario inserindo senha de confirmação vazia
        ErrorMessage responseBody = testClient.put()
                .uri("api/v1/restaurant/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassDto("123456", "654322", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

//		Usuario inserindo senha atual com um digito a menos
        responseBody = testClient.put()
                .uri("api/v1/restaurant/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassDto("12345", "654321", "654321"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);


    }

}
