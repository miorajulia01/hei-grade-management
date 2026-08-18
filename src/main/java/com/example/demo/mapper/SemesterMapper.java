package com.example.demo.mapper;

import com.example.demo.entity.JSemester;
import com.example.demo.model.Semester;

public class SemesterMapper {
  public static Semester toModel(JSemester entity) {
    if (entity == null) return null;
    return Semester.builder().id(entity.getId()).code(entity.getCode()).build();
  }

  public static JSemester toEntity(Semester model) {
    if (model == null) return null;
    return JSemester.builder().id(model.getId()).code(model.getCode()).build();
  }
}
