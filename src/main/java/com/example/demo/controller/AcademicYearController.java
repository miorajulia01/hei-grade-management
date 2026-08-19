package com.example.demo.controller;

import com.example.demo.model.AcademicYear;
import com.example.demo.service.AcademicYearService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/academic-years")
@RequiredArgsConstructor
public class AcademicYearController {

  private final AcademicYearService academicYearService;

  @GetMapping
  public List<AcademicYear> getAll() {
    return academicYearService.getAllAcademicYears();
  }

  @GetMapping("/{id}")
  public AcademicYear getById(@PathVariable String id) {
    return academicYearService.getAcademicYearById(id);
  }

  @PostMapping
  public AcademicYear create(@RequestBody AcademicYear academicYear) {
    return academicYearService.saveAcademicYear(academicYear);
  }

  @PutMapping("/{id}")
  public AcademicYear update(@PathVariable String id, @RequestBody AcademicYear academicYear) {
    return academicYearService.updateAcademicYear(id, academicYear);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    academicYearService.deleteAcademicYear(id);
    return ResponseEntity.noContent().build();
  }
}
