package com.example.demo.service;

import com.example.demo.entity.JGroup;
import com.example.demo.entity.JProgram;
import com.example.demo.mapper.GroupMapper;
import com.example.demo.model.Group;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.ProgramRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

  private final GroupRepository groupRepository;
  private final ProgramRepository programRepository;

  public List<Group> getAllGroups() {
    return groupRepository.findAll().stream()
            .map(GroupMapper::toModel)
            .toList();
  }

  public Group getGroupById(String id) {
    JGroup entity = groupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
    return GroupMapper.toModel(entity);
  }

  public Group saveGroup(Group model) {
    JProgram program = null;
    if (model.getProgram() != null && model.getProgram().getId() != null) {
      program = programRepository.findById(model.getProgram().getId())
              .orElseThrow(() -> new RuntimeException("Program not found"));
    }

    JGroup entity = JGroup.builder()
            .id(model.getId())
            .program(program)
            .ref(model.getRef())
            .build();

    JGroup saved = groupRepository.save(entity);
    return GroupMapper.toModel(saved);
  }
}