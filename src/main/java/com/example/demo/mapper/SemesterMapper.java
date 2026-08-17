package com.example.demo.mapper;

import com.example.demo.entity.JSemester;
import com.example.demo.model.Semester;

public class SemesterMapper {
    public static Semester toModel(JSemester entity) {
        if (entity == null) return null;
        return Semester.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .build();
    }
}