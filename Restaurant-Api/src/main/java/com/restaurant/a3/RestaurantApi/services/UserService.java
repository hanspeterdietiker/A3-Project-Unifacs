package com.restaurant.a3.RestaurantApi.services;

import com.restaurant.a3.RestaurantApi.exceptions.EmailUniqueViolationException;
import com.restaurant.a3.RestaurantApi.exceptions.EntityNotFoundException;
import com.restaurant.a3.RestaurantApi.exceptions.PasswordInvalidException;
import com.restaurant.a3.RestaurantApi.models.User;
import com.restaurant.a3.RestaurantApi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return  userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário de ID: '%s', não encontrado!", id))
        );
    }

    @Transactional
    public User saveUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }catch (DataIntegrityViolationException exception) {
            exception.printStackTrace();
            throw  new EmailUniqueViolationException(String.format("Email: '%s' já cadastrado!", user.getUsername()));
        }

    }

    @Transactional
    public User updatePassword(Long id, String password, String newPassword, String confirmPassword) {
        var olderUser = findById(id);

        if(!passwordEncoder.matches(password, olderUser.getPassword())) {
            throw new PasswordInvalidException("Digite a senha atual correta!");
        }

        if(!newPassword.equals(confirmPassword)) {
            throw new PasswordInvalidException("Senhas incompativeis!");
        }

        return userRepository.save(updatedCurrentPassword(olderUser, newPassword));

    }

    private User updatedCurrentPassword(User u1, String newPassword) {
        u1.setPassword(passwordEncoder.encode(newPassword));

        return u1;
    }

    public User findByUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário não encontrado | EMAIL: %s", username)));

        return user;
    }

    public User.Role findRoleByUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário não encontrado | EMAIL: %s", username)));

        return  user.getRole();
    }
}
