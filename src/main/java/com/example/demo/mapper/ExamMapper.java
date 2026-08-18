package com.example.demo.mapper;

import com.example.demo.entity.JExam;
import com.example.demo.model.Exam;

public class ExamMapper {
  public static Exam toModel(JExam entity) {
    if (entity == null) return null;
    return Exam.builder()
        .id(entity.getId())
        .course(CourseMapper.toModel(entity.getCourse()))
        .type(entity.getType())
        .title(entity.getTitle())
        .dateExam(entity.getDateExam())
        .coefficient(entity.getCoefficient())
        .order(entity.getOrder())
        .isPublished(entity.getIsPublished())
        .build();
  }

  public static JExam toEntity(Exam model) {
    if (model == null) return null;
    return JExam.builder()
        .id(model.getId())
        .course(CourseMapper.toEntity(model.getCourse()))
        .type(model.getType())
        .title(model.getTitle())
        .dateExam(model.getDateExam())
        .coefficient(model.getCoefficient())
        .order(model.getOrder())
        .isPublished(model.getIsPublished())
        .build();
  }
}
