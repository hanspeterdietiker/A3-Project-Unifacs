package com.restaurant.a3.RestaurantApi.services;

import com.restaurant.a3.RestaurantApi.exceptions.EntityNotFoundException;
import com.restaurant.a3.RestaurantApi.models.CommentModel;
import com.restaurant.a3.RestaurantApi.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


@DataJpaTest
@ActiveProfiles("test")
class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    CommentRepository commentRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar um comentário salvada no DB com sucesso")
    void saveCommentSucess() {
        String text = "Restaurante bem aromatizado";
        CommentModel comment = new CommentModel();
        comment.setId(1L);
        comment.setText(text);
        when(commentRepository.save(comment)).thenReturn(comment);

        CommentModel savedComment = commentService.saveComment(comment);

        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getId()).isEqualTo(1);
        assertThat(savedComment.getText()).isEqualTo("Restaurante bem aromatizado");
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    @DisplayName("Deve retornar uma lista de comentários de um usuário por id com sucesso")
    void getCommentsByUserIdSucees() {
        List<CommentModel> comments = new ArrayList<>();


        String text = "Restaurante bem aromatizado";
        CommentModel comment = new CommentModel();
        comment.setId(1L);
        comment.setText(text);

        String text2 = "Restaurante com ótima localização";
        CommentModel comment2 = new CommentModel();
        comment2.setId(2L);
        comment2.setText(text2);

        comments.add(comment);
        comments.add(comment2);
        when(commentRepository.findCommentsByUser(1L)).thenReturn(comments);

        List<CommentModel> getComments = commentService.getCommentsByUserId(1L);

        assertThat(getComments.isEmpty()).isFalse();
        assertThat(getComments.get(0)).isEqualTo(comment);
        assertThat(getComments.get(1)).isEqualTo(comment2);
        assertThat(getComments.size()).isEqualTo(2);
        verify(commentRepository, times(1)).findCommentsByUser(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar comentários de um usuário inexistente")
    void getCommentsByUserIdIsNotFound() {
        when(commentRepository.findCommentsByUser(1L)).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> commentService.getCommentsByUserId(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Usuário não encontrado!");
    }


}