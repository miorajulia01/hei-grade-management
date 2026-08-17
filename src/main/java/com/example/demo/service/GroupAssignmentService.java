package com.example.demo.service;

import com.example.demo.entity.JGroup;
import com.example.demo.entity.JGroupAssignment;
import com.example.demo.entity.JSemester;
import com.example.demo.entity.JStudent;
import com.example.demo.mapper.GroupAssignmentMapper;
import com.example.demo.model.GroupAssignment;
import com.example.demo.repository.GroupAssignmentRepository;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.SemesterRepository;
import com.example.demo.repository.StudentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupAssignmentService {

  private final GroupAssignmentRepository groupAssignmentRepository;
  private final StudentRepository studentRepository;
  private final GroupRepository groupRepository;
  private final SemesterRepository semesterRepository;

  public List<GroupAssignment> getAllGroupAssignments() {
    return groupAssignmentRepository.findAll().stream()
            .map(GroupAssignmentMapper::toModel)
            .toList();
  }

  public GroupAssignment getGroupAssignmentById(String id) {
    JGroupAssignment entity = groupAssignmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GroupAssignment not found with id: " + id));
    return GroupAssignmentMapper.toModel(entity);
  }

  public GroupAssignment saveGroupAssignment(GroupAssignment model) {
    JStudent student = null;
    if (model.getStudent() != null && model.getStudent().getId() != null) {
      student = studentRepository.findById(model.getStudent().getId())
              .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    JGroup group = null;
    if (model.getGroup() != null && model.getGroup().getId() != null) {
      group = groupRepository.findById(model.getGroup().getId())
              .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    JSemester semester = null;
    if (model.getSemester() != null && model.getSemester().getId() != null) {
      semester = semesterRepository.findById(model.getSemester().getId())
              .orElseThrow(() -> new RuntimeException("Semester not found"));
    }

    JGroupAssignment entity = JGroupAssignment.builder()
            .id(model.getId())
            .student(student)
            .group(group)
            .semester(semester)
            .isActive(model.getIsActive())
            .build();

    JGroupAssignment saved = groupAssignmentRepository.save(entity);
    return GroupAssignmentMapper.toModel(saved);
  }
}