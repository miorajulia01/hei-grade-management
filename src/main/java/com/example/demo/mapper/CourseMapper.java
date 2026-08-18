package com.example.demo.mapper;

import com.example.demo.entity.JCourse;
import com.example.demo.model.Course;

public class CourseMapper {
  public static Course toModel(JCourse entity) {
    if (entity == null) return null;
    return Course.builder()
            .id(entity.getId())
            .semester(SemesterMapper.toModel(entity.getSemester()))
            .program(ProgramMapper.toModel(entity.getProgram()))
            .ref(entity.getRef())
            .title(entity.getTitle())
            .credit(entity.getCredit())
            .type(entity.getType())
            .isActive(entity.getIsActive())
            .build();
  }

  public static JCourse toEntity(Course model) {
    if (model == null) return null;
    return JCourse.builder()
            .id(model.getId())
            .semester(SemesterMapper.toEntity(model.getSemester()))
            .program(ProgramMapper.toEntity(model.getProgram()))
            .ref(model.getRef())
            .title(model.getTitle())
            .credit(model.getCredit())
            .type(model.getType())
            .isActive(model.getIsActive())
            .build();
  }
}