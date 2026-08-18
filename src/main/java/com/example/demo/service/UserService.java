package com.example.demo.service;

import com.example.demo.entity.JUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public List<User> getAllUsers() {
    return userRepository.findAll().stream().map(UserMapper::toModel).toList();
  }

  public User getUserById(String id) {
    JUser entity =
        userRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    return UserMapper.toModel(entity);
  }

  public User saveUser(User model) {
    JUser entity =
        JUser.builder()
            .id(model.getId())
            .email(model.getEmail())
            .role(model.getRole())
            .status(model.getStatus())
            .build();
    JUser saved = userRepository.save(entity);
    return UserMapper.toModel(saved);
  }
}
