package com.example.demo.service;

import com.example.demo.entity.JExam;
import com.example.demo.entity.JGrade;
import com.example.demo.entity.JStudent;
import com.example.demo.mapper.GradeMapper;
import com.example.demo.model.Grade;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.GradeRepository;
import com.example.demo.repository.StudentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradeService {

  private final GradeRepository gradeRepository;
  private final StudentRepository studentRepository;
  private final ExamRepository examRepository;

  public List<Grade> getAllGrades() {
    return gradeRepository.findAll().stream().map(GradeMapper::toModel).toList();
  }

  public Grade getGradeById(String id) {
    JGrade entity =
        gradeRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Grade not found with id: " + id));
    return GradeMapper.toModel(entity);
  }

  public Grade saveGrade(Grade model) {
    JStudent student = null;
    if (model.getStudent() != null && model.getStudent().getId() != null) {
      student =
          studentRepository
              .findById(model.getStudent().getId())
              .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    JExam exam = null;
    if (model.getExam() != null && model.getExam().getId() != null) {
      exam =
          examRepository
              .findById(model.getExam().getId())
              .orElseThrow(() -> new RuntimeException("Exam not found"));
    }

    JGrade entity =
        JGrade.builder()
            .id(model.getId())
            .student(student)
            .exam(exam)
            .score(model.getScore())
            .weightedScore(model.getWeightedScore())
            .isValidated(model.getIsValidated())
            .validatedAt(model.getValidatedAt())
            .build();

    JGrade saved = gradeRepository.save(entity);
    return GradeMapper.toModel(saved);
  }
}
