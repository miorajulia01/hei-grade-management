package com.example.demo.mapper;

import com.example.demo.entity.JUser;
import com.example.demo.model.User;

public class UserMapper {
  public static User toModel(JUser entity) {
    if (entity == null) return null;
    return User.builder()
            .id(entity.getId())
            .email(entity.getEmail())
            .password(entity.getPassword())
            .role(entity.getRole())
            .createdAt(entity.getCreatedAt())
            .status(entity.getStatus())
            .build();
  }

  public static JUser toEntity(User model) {
    if (model == null) return null;
    return JUser.builder()
            .id(model.getId())
            .email(model.getEmail())
            .password(model.getPassword())
            .role(model.getRole())
            .createdAt(model.getCreatedAt())
            .status(model.getStatus())
            .build();
  }
}