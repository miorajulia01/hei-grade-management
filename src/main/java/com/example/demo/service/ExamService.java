package com.example.demo.service;

import com.example.demo.entity.JExam;
import com.example.demo.mapper.ExamMapper;
import com.example.demo.model.Exam;
import com.example.demo.repository.ExamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final ExamMapper examMapper;

    public ExamService(ExamRepository examRepository, ExamMapper examMapper) {
        this.examRepository = examRepository;
        this.examMapper = examMapper;
    }

    @Transactional(readOnly = true)
    public List<Exam> getExams(String courseId) {
        List<JExam> entities;

        if (courseId != null && !courseId.isBlank()) {
            entities = examRepository.findByCourseId(courseId);
        } else {
            entities = examRepository.findAll();
        }

        return entities.stream()
                .map(examMapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Exam getExamById(String id) {
        return examRepository.findById(id)
                .map(examMapper::toModel)
                .orElseThrow(() -> new IllegalArgumentException("Exam not found with ID: " + id));
    }
}