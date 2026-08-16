package com.example.demo.mapper;

import com.example.demo.entity.JUser;
import com.example.demo.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toModel(JUser entity) {
        if (entity == null) return null;
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .role(entity.getRole())
                .build();
    }
}