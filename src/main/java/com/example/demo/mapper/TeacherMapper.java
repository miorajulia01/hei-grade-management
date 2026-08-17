package com.example.demo.mapper;

import com.example.demo.entity.JTeacher;
import com.example.demo.model.Teacher;

public class TeacherMapper {
  public static Teacher toModel(JTeacher entity) {
    if (entity == null) return null;
    return Teacher.builder()
        .id(entity.getId())
        .user(UserMapper.toModel(entity.getUser()))
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .specialty(entity.getSpecialty())
        .status(entity.getStatus())
        .build();
  }
}
