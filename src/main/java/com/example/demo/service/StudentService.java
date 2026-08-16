package com.example.demo.service;

import com.example.demo.entity.JStudent;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Transactional(readOnly = true)
    public List<Student> getStudents(String firstName, String lastName, String studentNumber, String promotionId) {
        List<JStudent> entities;

        if (studentNumber != null && !studentNumber.isBlank()) {
            entities = studentRepository.findByStudentNumber(studentNumber)
                    .map(List::of)
                    .orElse(List.of());
        } else if (lastName != null && !lastName.isBlank()) {
            entities = studentRepository.findByLastNameContainingIgnoreCase(lastName);
        } else if (firstName != null && !firstName.isBlank()) {
            entities = studentRepository.findByFirstNameContainingIgnoreCase(firstName);
        } else if (promotionId != null && !promotionId.isBlank()) {
            entities = studentRepository.findByPromotionId(promotionId);
        } else {
            entities = studentRepository.findAll();
        }

        return entities.stream()
                .map(studentMapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .map(studentMapper::toModel)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));
    }
}