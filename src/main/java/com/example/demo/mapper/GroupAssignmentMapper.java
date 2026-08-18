package com.example.demo.mapper;

import com.example.demo.entity.JGroupAssignment;
import com.example.demo.model.GroupAssignment;

public class GroupAssignmentMapper {
  public static GroupAssignment toModel(JGroupAssignment entity) {
    if (entity == null) return null;
    return GroupAssignment.builder()
            .id(entity.getId())
            .student(StudentMapper.toModel(entity.getStudent()))
            .group(GroupMapper.toModel(entity.getGroup()))
            .semester(SemesterMapper.toModel(entity.getSemester()))
            .assignedAt(entity.getAssignedAt())
            .isActive(entity.getIsActive())
            .build();
  }

  public static JGroupAssignment toEntity(GroupAssignment model) {
    if (model == null) return null;
    return JGroupAssignment.builder()
            .id(model.getId())
            .student(StudentMapper.toEntity(model.getStudent()))
            .group(GroupMapper.toEntity(model.getGroup()))
            .semester(SemesterMapper.toEntity(model.getSemester()))
            .assignedAt(model.getAssignedAt())
            .isActive(model.getIsActive())
            .build();
  }
}