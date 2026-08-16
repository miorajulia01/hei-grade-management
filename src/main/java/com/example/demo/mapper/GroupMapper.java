package com.example.demo.mapper;

import com.example.demo.entity.JGroup;
import com.example.demo.model.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {
    public Group toModel(JGroup entity) {
        if (entity == null) return null;
        return Group.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}