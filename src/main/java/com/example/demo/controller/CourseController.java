package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

  private final CourseService courseService;

  @GetMapping
  public List<Course> getAll() {
    return courseService.getAllCourses();
  }

  @GetMapping("/{id}")
  public Course getById(@PathVariable String id) {
    return courseService.getCourseById(id);
  }

  @PostMapping
  public Course create(@RequestBody Course course) {
    return courseService.saveCourse(course);
  }

  @PutMapping("/{id}")
  public Course update(@PathVariable String id, @RequestBody Course course) {
    return courseService.updateCourse(id, course);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    courseService.deleteCourse(id);
    return ResponseEntity.noContent().build();
  }
}
