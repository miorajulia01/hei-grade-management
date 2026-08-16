package com.example.demo.service;

import com.example.demo.entity.JTeacher;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.model.Teacher;
import com.example.demo.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    @Transactional(readOnly = true)
    public List<Teacher> getTeachers(String firstName, String lastName) {
        List<JTeacher> entities;

        if (lastName != null && !lastName.isBlank()) {
            entities = teacherRepository.findByLastNameContainingIgnoreCase(lastName);
        } else if (firstName != null && !firstName.isBlank()) {
            entities = teacherRepository.findByFirstNameContainingIgnoreCase(firstName);
        } else {
            entities = teacherRepository.findAll();
        }

        return entities.stream()
                .map(teacherMapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Teacher getTeacherById(String id) {
        return teacherRepository.findById(id)
                .map(teacherMapper::toModel)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + id));
    }
}