package com.example.demo.mapper;

import com.example.demo.entity.JGrade;
import com.example.demo.model.Grade;
import org.springframework.stereotype.Component;

@Component
public class GradeMapper {
  public Grade toModel(JGrade entity) {
    if (entity == null) return null;
    return Grade.builder()
        .id(entity.getId())
        .score(entity.getScore())
        .studentId(entity.getStudent() != null ? entity.getStudent().getId() : null)
        .examId(entity.getExam() != null ? entity.getExam().getId() : null)
        .build();
  }
}
