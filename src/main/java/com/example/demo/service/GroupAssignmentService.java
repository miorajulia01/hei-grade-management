package com.example.demo.service;

import com.example.demo.entity.JGroupAssignment;
import com.example.demo.mapper.GroupAssignmentMapper;
import com.example.demo.model.GroupAssignment;
import com.example.demo.repository.GroupAssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupAssignmentService {

    private final GroupAssignmentRepository groupAssignmentRepository;
    private final GroupAssignmentMapper groupAssignmentMapper;

    public GroupAssignmentService(
            GroupAssignmentRepository groupAssignmentRepository,
            GroupAssignmentMapper groupAssignmentMapper) {
        this.groupAssignmentRepository = groupAssignmentRepository;
        this.groupAssignmentMapper = groupAssignmentMapper;
    }

    @Transactional(readOnly = true)
    public List<GroupAssignment> getAssignments(String studentId, String groupId) {
        List<JGroupAssignment> entities;

        if (studentId != null && !studentId.isBlank()) {
            entities = groupAssignmentRepository.findByStudentId(studentId);
        } else if (groupId != null && !groupId.isBlank()) {
            entities = groupAssignmentRepository.findByGroupId(groupId);
        } else {
            entities = groupAssignmentRepository.findAll();
        }

        return entities.stream()
                .map(groupAssignmentMapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GroupAssignment getAssignmentById(String id) {
        return groupAssignmentRepository.findById(id)
                .map(groupAssignmentMapper::toModel)
                .orElseThrow(() -> new IllegalArgumentException("Group assignment not found with ID: " + id));
    }
}