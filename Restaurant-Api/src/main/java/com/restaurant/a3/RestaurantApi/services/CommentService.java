package com.restaurant.a3.RestaurantApi.services;

import com.restaurant.a3.RestaurantApi.exceptions.EntityNotFoundException;
import com.restaurant.a3.RestaurantApi.models.CommentModel;
import com.restaurant.a3.RestaurantApi.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public CommentModel saveComment(CommentModel comment) {
        return commentRepository.save(comment);
    }

    public List<CommentModel> getCommentsByUserId(Long id) {
        try {

            return commentRepository.findCommentsByUser(id);
        }catch (DataIntegrityViolationException exception) {
            throw new EntityNotFoundException("Usuário não encontrado!");
        }
    }

}
