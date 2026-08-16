package com.example.demo.mapper;

import com.example.demo.entity.JAffectationGroupe;
import com.example.demo.model.GroupAssignment;
import org.springframework.stereotype.Component;

@Component
public class GroupAssignmentMapper {
    public GroupAssignment toModel(JAffectationGroupe entity) {
        if (entity == null) return null;
        return GroupAssignment.builder()
                .id(entity.getId())
                .studentId(entity.getStudent() != null ? entity.getStudent().getId() : null)
                .groupId(entity.getGroup() != null ? entity.getGroup().getId() : null)
                .build();
    }
}