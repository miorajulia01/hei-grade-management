package com.example.demo.mapper;

import com.example.demo.entity.JStudent;
import com.example.demo.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public Student toModel(JStudent entity) {
        if (entity == null) return null;
        return Student.builder()
                .id(entity.getId())
                .studentNumber(entity.getStudentNumber())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getUser() != null ? entity.getUser().getEmail() : null)
                .promotionName(entity.getPromotion() != null ? entity.getPromotion().getName() : null)
                .build();
    }
}