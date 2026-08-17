package com.example.demo.mapper;

import com.example.demo.entity.JCourseTeacher;
import com.example.demo.model.CourseTeacher;

public class CourseTeacherMapper {
  public static CourseTeacher toModel(JCourseTeacher entity) {
    if (entity == null) return null;
    return CourseTeacher.builder()
        .id(entity.getId())
        .course(CourseMapper.toModel(entity.getCourse()))
        .teacher(TeacherMapper.toModel(entity.getTeacher()))
        .assignedAt(entity.getAssignedAt())
        .isPrimary(entity.getIsPrimary())
        .build();
  }
}
