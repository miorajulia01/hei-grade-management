package com.example.demo.mapper;

import com.example.demo.entity.JCourse;
import com.example.demo.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
  public Course toModel(JCourse entity) {
    if (entity == null) return null;
    return Course.builder()
        .id(entity.getId())
        .code(entity.getCode())
        .name(entity.getName())
        .credits(entity.getCredits())
        .build();
  }
}
