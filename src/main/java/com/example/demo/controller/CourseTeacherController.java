package com.example.demo.controller;

import com.example.demo.model.CourseTeacher;
import com.example.demo.service.CourseTeacherService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course-teachers")
@RequiredArgsConstructor
public class CourseTeacherController {

  private final CourseTeacherService courseTeacherService;

  @GetMapping
  public List<CourseTeacher> getAll() {
    return courseTeacherService.getAllCourseTeachers();
  }

  @GetMapping("/{courseId}/{teacherId}")
  public CourseTeacher getById(@PathVariable String courseId, @PathVariable String teacherId) {
    return courseTeacherService.getCourseTeacherById(courseId, teacherId);
  }

  @PostMapping
  public CourseTeacher create(@RequestBody CourseTeacher courseTeacher) {
    return courseTeacherService.saveCourseTeacher(courseTeacher);
  }

  @PutMapping("/{courseId}/{teacherId}")
  public CourseTeacher update(
      @PathVariable String courseId,
      @PathVariable String teacherId,
      @RequestBody CourseTeacher courseTeacher) {
    return courseTeacherService.updateCourseTeacher(courseId, teacherId, courseTeacher);
  }

  @DeleteMapping("/{courseId}/{teacherId}")
  public ResponseEntity<Void> delete(
      @PathVariable String courseId, @PathVariable String teacherId) {
    courseTeacherService.deleteCourseTeacher(courseId, teacherId);
    return ResponseEntity.noContent().build();
  }
}
