package com.example.demo.controller;

import com.example.demo.model.GradeHistory;
import com.example.demo.service.GradeHistoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grade-histories")
@RequiredArgsConstructor
public class GradeHistoryController {

  private final GradeHistoryService gradeHistoryService;

  @GetMapping
  public List<GradeHistory> getAll() {
    return gradeHistoryService.getAllGradeHistories();
  }

  @GetMapping("/{id}")
  public GradeHistory getById(@PathVariable String id) {
    return gradeHistoryService.getGradeHistoryById(id);
  }

  @GetMapping("/grade/{gradeId}")
  public List<GradeHistory> getByGrade(@PathVariable String gradeId) {
    return gradeHistoryService.getGradeHistoriesByGrade(gradeId);
  }
}
