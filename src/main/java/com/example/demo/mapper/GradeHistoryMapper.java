package com.example.demo.mapper;

import com.example.demo.entity.JGradeHistory;
import com.example.demo.model.GradeHistory;
import org.springframework.stereotype.Component;

@Component
public class GradeHistoryMapper {
    public GradeHistory toModel(JGradeHistory entity) {
        if (entity == null) return null;
        return GradeHistory.builder()
                .id(entity.getId())
                .oldScore(entity.getOldScore())
                .newScore(entity.getNewScore())
                .reason(entity.getReason())
                .updatedAt(entity.getUpdatedAt())
                .teacherId(entity.getTeacher() != null ? entity.getTeacher().getId() : null)
                .build();
    }
}