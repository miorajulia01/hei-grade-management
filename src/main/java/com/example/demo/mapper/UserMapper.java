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
}