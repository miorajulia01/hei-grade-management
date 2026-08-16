package com.example.demo.mapper;

import com.example.demo.entity.JExamen;
import com.example.demo.model.Exam;
import org.springframework.stereotype.Component;

@Component
public class ExamMapper {
    public Exam toModel(JExamen entity) {
        if (entity == null) return null;
        return Exam.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .coefficient(entity.getCoefficient())
                .courseId(entity.getCourse() != null ? entity.getCourse().getId() : null)
                .build();
    }
}