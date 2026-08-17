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
}