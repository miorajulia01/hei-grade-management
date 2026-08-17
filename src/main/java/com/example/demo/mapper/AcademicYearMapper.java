package com.example.demo.mapper;

import com.example.demo.entity.JAcademicYear;
import com.example.demo.model.AcademicYear;

public class AcademicYearMapper {
    public static AcademicYear toModel(JAcademicYear entity) {
        if (entity == null) return null;
        return AcademicYear.builder()
                .id(entity.getId())
                .label(entity.getLabel())
                .build();
    }
}