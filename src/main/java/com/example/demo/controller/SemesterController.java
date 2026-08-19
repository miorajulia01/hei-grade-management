package com.example.demo.controller;

import com.example.demo.model.Semester;
import com.example.demo.service.SemesterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/semesters")
@RequiredArgsConstructor
public class SemesterController {

  private final SemesterService semesterService;

  @GetMapping
  public List<Semester> getAll() {
    return semesterService.getAllSemesters();
  }

  @GetMapping("/{id}")
  public Semester getById(@PathVariable String id) {
    return semesterService.getSemesterById(id);
  }

  @PostMapping
  public Semester create(@RequestBody Semester semester) {
    return semesterService.saveSemester(semester);
  }

  @PutMapping("/{id}")
  public Semester update(@PathVariable String id, @RequestBody Semester semester) {
    return semesterService.updateSemester(id, semester);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    semesterService.deleteSemester(id);
    return ResponseEntity.noContent().build();
  }
}
