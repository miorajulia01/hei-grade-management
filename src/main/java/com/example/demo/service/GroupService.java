package com.example.demo.service;

import com.example.demo.entity.JGroup;
import com.example.demo.mapper.GroupMapper;
import com.example.demo.model.Group;
import com.example.demo.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @Transactional(readOnly = true)
    public List<Group> getGroups(String name, String ref) {
        List<JGroup> entities;

        if (ref != null && !ref.isBlank()) {
            entities = groupRepository.findByRef(ref)
                    .map(List::of)
                    .orElse(List.of());
        } else if (name != null && !name.isBlank()) {
            entities = groupRepository.findByNameContainingIgnoreCase(name);
        } else {
            entities = groupRepository.findAll();
        }

        return entities.stream()
                .map(groupMapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Group getGroupById(String id) {
        return groupRepository.findById(id)
                .map(groupMapper::toModel)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + id));
    }
}