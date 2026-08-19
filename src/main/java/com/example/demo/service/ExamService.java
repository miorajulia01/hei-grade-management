package com.example.demo.service;

import com.example.demo.entity.JCourse;
import com.example.demo.entity.JExam;
import com.example.demo.mapper.ExamMapper;
import com.example.demo.model.Exam;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.ExamRepository;
import com.example.demo.validator.ExamValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {

  private final ExamRepository examRepository;
  private final CourseRepository courseRepository;

  public List<Exam> getAllExams() {
    return examRepository.findAll().stream().map(ExamMapper::toModel).toList();
  }

  public Exam getExamById(String id) {
    JExam entity =
        examRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));
    return ExamMapper.toModel(entity);
  }

  public Exam saveExam(Exam model) {
    ExamValidator.validate(model);

    JCourse course = null;
    if (model.getCourse() != null && model.getCourse().getId() != null) {
      course =
          courseRepository
              .findById(model.getCourse().getId())
              .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    JExam entity =
        JExam.builder()
            .id(model.getId())
            .course(course)
            .type(model.getType())
            .title(model.getTitle())
            .dateExam(model.getDateExam())
            .coefficient(model.getCoefficient())
            .order(model.getOrder())
            .isPublished(model.getIsPublished())
            .build();

    JExam saved = examRepository.save(entity);
    return ExamMapper.toModel(saved);
  }

  public Exam updateExam(String id, Exam model) {
    JExam existing =
        examRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));

    if (model.getCourse() != null && model.getCourse().getId() != null) {
      JCourse course =
          courseRepository
              .findById(model.getCourse().getId())
              .orElseThrow(() -> new RuntimeException("Course not found"));
      existing.setCourse(course);
    }
    existing.setType(model.getType());
    existing.setTitle(model.getTitle());
    existing.setDateExam(model.getDateExam());
    existing.setCoefficient(model.getCoefficient());
    existing.setOrder(model.getOrder());
    existing.setIsPublished(model.getIsPublished());

    JExam saved = examRepository.save(existing);
    return ExamMapper.toModel(saved);
  }

  public void deleteExam(String id) {
    if (!examRepository.existsById(id)) {
      throw new RuntimeException("Exam not found with id: " + id);
    }
    examRepository.deleteById(id);
  }
}
