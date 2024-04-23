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

    @Transactional(readOnly = true)
    public List<CommentModel> getCommentsByUserId(Long id) {
        List<CommentModel> comments = commentRepository.findCommentsByUser(id);
        if (comments.isEmpty()) {
            throw new EntityNotFoundException("Usuário não encontrado!");
        }
        return comments;

    }

}
