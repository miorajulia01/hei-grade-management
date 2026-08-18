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

  public static JGradeHistory toEntity(GradeHistory model) {
    if (model == null) return null;
    return JGradeHistory.builder()
        .id(model.getId())
        .grade(GradeMapper.toEntity(model.getGrade()))
        .teacher(TeacherMapper.toEntity(model.getTeacher()))
        .oldScore(model.getOldScore())
        .newScore(model.getNewScore())
        .reason(model.getReason())
        .modifiedAt(model.getModifiedAt())
        .build();
  }
}
