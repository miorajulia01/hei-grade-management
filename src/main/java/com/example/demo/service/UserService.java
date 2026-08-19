package com.example.demo.service;

import com.example.demo.entity.JUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

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
    String encodedPassword =
        model.getPassword() == null || model.getPassword().isBlank()
            ? null
            : passwordEncoder.encode(model.getPassword());

    JUser entity =
        JUser.builder()
            .id(model.getId())
            .email(model.getEmail())
            .password(encodedPassword)
            .role(model.getRole())
            .status(model.getStatus())
            .createdAt(model.getCreatedAt() == null ? Instant.now() : model.getCreatedAt())
            .build();
    JUser saved = userRepository.save(entity);
    return UserMapper.toModel(saved);
  }

  public User updateUser(String id, User model) {
    JUser existing =
        userRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

    if (model.getEmail() != null) {
      existing.setEmail(model.getEmail());
    }
    if (model.getPassword() != null && !model.getPassword().isBlank()) {
      existing.setPassword(passwordEncoder.encode(model.getPassword()));
    }
    if (model.getRole() != null) {
      existing.setRole(model.getRole());
    }
    if (model.getStatus() != null) {
      existing.setStatus(model.getStatus());
    }
    JUser saved = userRepository.save(existing);
    return UserMapper.toModel(saved);
  }

  public void deleteUser(String id) {
    if (!userRepository.existsById(id)) {
      throw new RuntimeException("User not found with id: " + id);
    }
    userRepository.deleteById(id);
  }
}
