package com.example.demo.mapper;

import com.example.demo.entity.JGrade;
import com.example.demo.model.Grade;

public class GradeMapper {
  public static Grade toModel(JGrade entity) {
    if (entity == null) return null;
    return Grade.builder()
            .id(entity.getId())
            .student(StudentMapper.toModel(entity.getStudent()))
            .exam(ExamMapper.toModel(entity.getExam()))
            .score(entity.getScore())
            .weightedScore(entity.getWeightedScore())
            .isValidated(entity.getIsValidated())
            .validatedAt(entity.getValidatedAt())
            .build();
  }

  public static JGrade toEntity(Grade model) {
    if (model == null) return null;
    return JGrade.builder()
            .id(model.getId())
            .student(StudentMapper.toEntity(model.getStudent()))
            .exam(ExamMapper.toEntity(model.getExam()))
            .score(model.getScore())
            .weightedScore(model.getWeightedScore())
            .isValidated(model.getIsValidated())
            .validatedAt(model.getValidatedAt())
            .build();
  }
}