package com.example.demo.mapper;

import com.example.demo.entity.JTeacher;
import com.example.demo.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
  public Teacher toModel(JTeacher entity) {
    if (entity == null) return null;
    return Teacher.builder()
        .id(entity.getId())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .email(entity.getUser() != null ? entity.getUser().getEmail() : null)
        .build();
  }
}
