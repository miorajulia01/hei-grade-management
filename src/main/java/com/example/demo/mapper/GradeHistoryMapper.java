package com.example.demo.mapper;

import com.example.demo.entity.JGradeHistory;
import com.example.demo.model.GradeHistory;

public class GradeHistoryMapper {
  public static GradeHistory toModel(JGradeHistory entity) {
    if (entity == null) return null;
    return GradeHistory.builder()
        .id(entity.getId())
        .grade(GradeMapper.toModel(entity.getGrade()))
        .teacher(TeacherMapper.toModel(entity.getTeacher()))
        .oldScore(entity.getOldScore())
        .newScore(entity.getNewScore())
        .reason(entity.getReason())
        .modifiedAt(entity.getModifiedAt())
        .build();
  }
}
