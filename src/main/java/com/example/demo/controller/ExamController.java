package com.example.demo.controller;

import com.example.demo.model.Exam;
import com.example.demo.service.AccessControlService;
import com.example.demo.service.ExamService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {

  private final ExamService examService;
  private final AccessControlService accessControlService;

  @GetMapping
  public List<Exam> getAll() {
    return examService.getAllExams();
  }

  @GetMapping("/{id}")
  public Exam getById(@PathVariable String id) {
    return examService.getExamById(id);
  }

  @PostMapping
  public Exam create(@RequestBody Exam exam) {
    accessControlService.assertTeachesCourseOrAdmin(exam.getCourse().getId());
    return examService.saveExam(exam);
  }

  @PutMapping("/{id}")
  public Exam update(@PathVariable String id, @RequestBody Exam exam) {
    Exam existing = examService.getExamById(id);
    accessControlService.assertTeachesCourseOrAdmin(existing.getCourse().getId());
    return examService.updateExam(id, exam);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    examService.deleteExam(id);
    return ResponseEntity.noContent().build();
  }
}
