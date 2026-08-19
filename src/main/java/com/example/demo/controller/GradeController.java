package com.example.demo.controller;

import com.example.demo.dto.UpdateGradeDto;
import com.example.demo.model.Grade;
import com.example.demo.service.AccessControlService;
import com.example.demo.service.GradeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {

  private final GradeService gradeService;
  private final AccessControlService accessControlService;

  @GetMapping
  public List<Grade> getAll() {
    return gradeService.getAllGrades();
  }

  @GetMapping("/{id}")
  public Grade getById(@PathVariable String id) {
    Grade grade = gradeService.getGradeById(id);
    accessControlService.assertOwnStudentOrStaff(grade.getStudent().getId());
    return grade;
  }

  @PostMapping
  public Grade create(@RequestBody Grade grade) {
    accessControlService.assertTeachesCourseOrAdmin(grade.getExam().getCourse().getId());
    return gradeService.saveGrade(grade);
  }

  @PutMapping("/{id}")
  public Grade update(@PathVariable String id, @Valid @RequestBody UpdateGradeDto dto) {
    Grade existing = gradeService.getGradeById(id);
    accessControlService.assertTeachesCourseOrAdmin(existing.getExam().getCourse().getId());
    String requesterEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    return gradeService.updateGrade(id, dto.getScore(), dto.getReason(), requesterEmail);
  }

  @GetMapping("/student/{studentId}")
  public List<Grade> getByStudent(@PathVariable String studentId) {
    accessControlService.assertOwnStudentOrStaff(studentId);
    return gradeService.getGradesByStudent(studentId);
  }

  @GetMapping("/student/{studentId}/average")
  public Double getWeightedAverage(@PathVariable String studentId) {
    accessControlService.assertOwnStudentOrStaff(studentId);
    return gradeService.calculateWeightedAverage(studentId);
  }

  @GetMapping("/student/{studentId}/retakes")
  public List<Grade> getRetakes(@PathVariable String studentId) {
    accessControlService.assertOwnStudentOrStaff(studentId);
    return gradeService.getRetakeGradesForStudent(studentId);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    gradeService.deleteGrade(id);
    return ResponseEntity.noContent().build();
  }
}
