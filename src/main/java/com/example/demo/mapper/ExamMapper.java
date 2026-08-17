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
}
