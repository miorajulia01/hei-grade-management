package com.example.demo.controller;

import com.example.demo.model.Teacher;
import com.example.demo.service.TeacherService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

  private final TeacherService teacherService;

  @GetMapping
  public List<Teacher> getAll() {
    return teacherService.getAllTeachers();
  }

  @GetMapping("/{id}")
  public Teacher getById(@PathVariable String id) {
    return teacherService.getTeacherById(id);
  }

  @PostMapping
  public Teacher create(@RequestBody Teacher teacher) {
    return teacherService.saveTeacher(teacher);
  }

  @PutMapping("/{id}")
  public Teacher update(@PathVariable String id, @RequestBody Teacher teacher) {
    return teacherService.updateTeacher(id, teacher);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    teacherService.deleteTeacher(id);
    return ResponseEntity.noContent().build();
  }
}
