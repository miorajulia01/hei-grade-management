package com.example.demo.mapper;

import com.example.demo.entity.JStudent;
import com.example.demo.model.Student;

public class StudentMapper {
  public static Student toModel(JStudent entity) {
    if (entity == null) return null;
    return Student.builder()
            .id(entity.getId())
            .promotion(PromotionMapper.toModel(entity.getPromotion()))
            .user(UserMapper.toModel(entity.getUser()))
            .studentNumber(entity.getStudentNumber())
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .email(entity.getEmail())
            .status(entity.getStatus())
            .dateEnroll(entity.getDateEnroll())
            .build();
  }

  public static JStudent toEntity(Student model) {
    if (model == null) return null;
    return JStudent.builder()
            .id(model.getId())
            .promotion(PromotionMapper.toEntity(model.getPromotion()))
            .user(UserMapper.toEntity(model.getUser()))
            .studentNumber(model.getStudentNumber())
            .firstName(model.getFirstName())
            .lastName(model.getLastName())
            .email(model.getEmail())
            .status(model.getStatus())
            .dateEnroll(model.getDateEnroll())
            .build();
  }
}