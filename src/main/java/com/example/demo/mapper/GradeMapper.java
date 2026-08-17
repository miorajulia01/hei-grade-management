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
}