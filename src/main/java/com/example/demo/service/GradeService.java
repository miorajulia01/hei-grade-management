package com.example.demo.service;

import com.example.demo.entity.JExam;
import com.example.demo.entity.JGrade;
import com.example.demo.entity.JGradeHistory;
import com.example.demo.entity.JStudent;
import com.example.demo.entity.JTeacher;
import com.example.demo.entity.JUser;
import com.example.demo.mapper.GradeMapper;
import com.example.demo.model.Grade;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.GradeHistoryRepository;
import com.example.demo.repository.GradeRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.validator.GradeValidator;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradeService {

  private final GradeRepository gradeRepository;
  private final StudentRepository studentRepository;
  private final ExamRepository examRepository;
  private final GradeHistoryRepository gradeHistoryRepository;
  private final TeacherRepository teacherRepository;
  private final UserRepository userRepository;

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

  public List<Grade> getGradesByStudent(String studentId) {
    return gradeRepository.findByStudentId(studentId).stream().map(GradeMapper::toModel).toList();
  }

  public Grade saveGrade(Grade model) {
    GradeValidator.validate(model);

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
            .isValidated(!GradeValidator.isRetake(model.getScore()))
            .validatedAt(model.getValidatedAt())
            .build();

    JGrade saved = gradeRepository.save(entity);
    return GradeMapper.toModel(saved);
  }

  public Grade updateGrade(String gradeId, Double newScore, String reason, String requesterEmail) {
    GradeValidator.validateUpdate(newScore, reason);

    JGrade grade =
        gradeRepository
            .findById(gradeId)
            .orElseThrow(() -> new RuntimeException("Grade not found with id: " + gradeId));

    JTeacher teacher = resolveTeacherFromEmail(requesterEmail);

    JGradeHistory history =
        JGradeHistory.builder()
            .grade(grade)
            .teacher(teacher)
            .oldScore(grade.getScore())
            .newScore(newScore)
            .reason(reason)
            .modifiedAt(Instant.now())
            .build();
    gradeHistoryRepository.save(history);

    grade.setScore(newScore);
    grade.setIsValidated(!GradeValidator.isRetake(newScore));
    grade.setValidatedAt(Instant.now());
    JGrade saved = gradeRepository.save(grade);
    return GradeMapper.toModel(saved);
  }

  private JTeacher resolveTeacherFromEmail(String email) {
    if (email == null) {
      return null;
    }
    JUser user = userRepository.findByEmailIgnoreCase(email).orElse(null);
    if (user == null) {
      return null;
    }
    return teacherRepository.findByUserId(user.getId()).orElse(null);
  }

  public Double calculateWeightedAverage(String studentId) {
    List<JGrade> grades = gradeRepository.findByStudentId(studentId);
    double totalWeightedPoints = 0.0;
    double totalCoefficients = 0.0;

    for (JGrade grade : grades) {
      if (grade.getExam() != null) {
        double coef = grade.getExam().getCoefficient();
        totalWeightedPoints += (grade.getScore() * coef);
        totalCoefficients += coef;
      }
    }

    return totalCoefficients == 0 ? 0.0 : (totalWeightedPoints / totalCoefficients);
  }

  public List<Grade> getRetakeGradesForStudent(String studentId) {
    return gradeRepository.findByStudentId(studentId).stream()
        .filter(grade -> GradeValidator.isRetake(grade.getScore()))
        .map(GradeMapper::toModel)
        .toList();
  }
}
