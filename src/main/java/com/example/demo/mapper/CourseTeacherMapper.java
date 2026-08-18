package com.example.demo.mapper;

import com.example.demo.entity.JCourseTeacher;
import com.example.demo.model.CourseTeacher;

public class CourseTeacherMapper {
  public static CourseTeacher toModel(JCourseTeacher entity) {
    if (entity == null) return null;
    return CourseTeacher.builder()
        .course(CourseMapper.toModel(entity.getCourse()))
        .teacher(TeacherMapper.toModel(entity.getTeacher()))
        .assignedAt(entity.getAssignedAt())
        .isPrimary(entity.getIsPrimary())
        .build();
  }

  public static JCourseTeacher toEntity(CourseTeacher model) {
    if (model == null) return null;
    return JCourseTeacher.builder()
        .course(CourseMapper.toEntity(model.getCourse()))
        .teacher(TeacherMapper.toEntity(model.getTeacher()))
        .assignedAt(model.getAssignedAt())
        .isPrimary(model.getIsPrimary())
        .build();
  }
}
