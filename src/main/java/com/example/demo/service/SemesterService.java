package com.example.demo.service;

import com.example.demo.entity.JSemester;
import com.example.demo.mapper.SemesterMapper;
import com.example.demo.model.Semester;
import com.example.demo.repository.SemesterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;

    public SemesterService(SemesterRepository semesterRepository, SemesterMapper semesterMapper) {
        this.semesterRepository = semesterRepository;
        this.semesterMapper = semesterMapper;
    }

    @Transactional(readOnly = true)
    public List<Semester> getSemesters(String name) {
        List<JSemester> entities;

        if (name != null && !name.isBlank()) {
            entities = semesterRepository.findByName(name)
                    .map(List::of)
                    .orElse(List.of());
        } else {
            entities = semesterRepository.findAll();
        }

        return entities.stream()
                .map(semesterMapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Semester getSemesterById(String id) {
        return semesterRepository.findById(id)
                .map(semesterMapper::toModel)
                .orElseThrow(() -> new IllegalArgumentException("Semester not found with ID: " + id));
    }
}