package com.example.demo.mapper;

import com.example.demo.entity.JGroup;
import com.example.demo.model.Group;

public class GroupMapper {
  public static Group toModel(JGroup entity) {
    if (entity == null) return null;
    return Group.builder()
        .id(entity.getId())
        .ref(entity.getRef())
        .capacity(entity.getCapacity())
        .program(ProgramMapper.toModel(entity.getProgram()))
        .build();
  }

  public static JGroup toEntity(Group model) {
    if (model == null) return null;
    return JGroup.builder()
        .id(model.getId())
        .ref(model.getRef())
        .capacity(model.getCapacity())
        .program(ProgramMapper.toEntity(model.getProgram()))
        .build();
  }
}
