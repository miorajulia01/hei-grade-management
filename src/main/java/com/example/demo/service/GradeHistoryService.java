package com.example.demo.service;

import com.example.demo.entity.JGrade;
import com.example.demo.entity.JGradeHistory;
import com.example.demo.entity.JTeacher;
import com.example.demo.mapper.GradeHistoryMapper;
import com.example.demo.model.GradeHistory;
import com.example.demo.repository.GradeHistoryRepository;
import com.example.demo.repository.GradeRepository;
import com.example.demo.repository.TeacherRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradeHistoryService {

  private final GradeHistoryRepository gradeHistoryRepository;
  private final GradeRepository gradeRepository;
  private final TeacherRepository teacherRepository;

  public List<GradeHistory> getAllGradeHistories() {
    return gradeHistoryRepository.findAll().stream()
            .map(GradeHistoryMapper::toModel)
            .toList();
  }

  public GradeHistory getGradeHistoryById(String id) {
    JGradeHistory entity = gradeHistoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GradeHistory not found with id: " + id));
    return GradeHistoryMapper.toModel(entity);
  }

  public GradeHistory saveGradeHistory(GradeHistory model) {
    JGrade grade = null;
    if (model.getGrade() != null && model.getGrade().getId() != null) {
      grade = gradeRepository.findById(model.getGrade().getId())
              .orElseThrow(() -> new RuntimeException("Grade not found"));
    }

    JTeacher teacher = null;
    if (model.getTeacher() != null && model.getTeacher().getId() != null) {
      teacher = teacherRepository.findById(model.getTeacher().getId())
              .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    JGradeHistory entity = JGradeHistory.builder()
            .id(model.getId())
            .grade(grade)
            .teacher(teacher)
            .oldScore(model.getOldScore())
            .newScore(model.getNewScore())
            .reason(model.getReason())
            .modifiedAt(model.getModifiedAt())
            .build();

    JGradeHistory saved = gradeHistoryRepository.save(entity);
    return GradeHistoryMapper.toModel(saved);
  }
}