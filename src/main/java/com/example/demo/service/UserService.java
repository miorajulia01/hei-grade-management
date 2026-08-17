package com.example.demo.service;

import com.example.demo.entity.JUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public List<User> getUsers(String email) {
        List<JUser> entities;

        if (email != null && !email.isBlank()) {
            entities = userRepository.findByEmail(email)
                    .map(List::of)
                    .orElse(List.of());
        } else {
            entities = userRepository.findAll();
        }

        return entities.stream()
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public User getUserById(String id) {
        return userRepository.findById(id)
                .map(userMapper::toModel)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }
}