package com.example.demo.mapper;

import com.example.demo.entity.JSemester;
import com.example.demo.model.Semester;
import org.springframework.stereotype.Component;

@Component
public class SemesterMapper {
    public Semester toModel(JSemester entity) {
        if (entity == null) return null;
        return Semester.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}