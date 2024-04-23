package com.restaurant.a3.RestaurantApi.services;

import com.restaurant.a3.RestaurantApi.models.AssessmentModel;
import com.restaurant.a3.RestaurantApi.models.RestaurantModel;
import com.restaurant.a3.RestaurantApi.models.UserModel;
import com.restaurant.a3.RestaurantApi.repositories.AssessmentRepository;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@DataJpaTest
@ActiveProfiles("test")
class AssessmentServiceTest {

    @InjectMocks
    AssessmentService assessmentService;

    @MockBean
    AssessmentRepository assessmentRepository;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar uma avaliação salvada no DB com sucesso")
    void saveSucess() {
        AssessmentModel assessment = new AssessmentModel(1L, 1, 1, 1, "userTest", null, null);

        when(assessmentRepository.save(any())).thenReturn(assessment);

        AssessmentModel savedAssessment = assessmentService.save(assessment);

        assertThat(savedAssessment).isNotNull();
        assertThat(savedAssessment.getId()).isEqualTo(1);

        verify(assessmentRepository, times(1)).save(assessment);

    }


}