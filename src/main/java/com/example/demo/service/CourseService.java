package com.example.demo.service;

import com.example.demo.entity.JCourse;
import com.example.demo.entity.JProgram;
import com.example.demo.entity.JSemester;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.ProgramRepository;
import com.example.demo.repository.SemesterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

  private final CourseRepository courseRepository;
  private final ProgramRepository programRepository;
  private final SemesterRepository semesterRepository;

  public List<Course> getAllCourses() {
    return courseRepository.findAll().stream()
            .map(CourseMapper::toModel)
            .toList();
  }

  public Course getCourseById(String id) {
    JCourse entity = courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    return CourseMapper.toModel(entity);
  }

  public Course saveCourse(Course model) {
    JProgram program = null;
    if (model.getProgram() != null && model.getProgram().getId() != null) {
      program = programRepository.findById(model.getProgram().getId())
              .orElseThrow(() -> new RuntimeException("Program not found"));
    }

    JSemester semester = null;
    if (model.getSemester() != null && model.getSemester().getId() != null) {
      semester = semesterRepository.findById(model.getSemester().getId())
              .orElseThrow(() -> new RuntimeException("Semester not found"));
    }

    JCourse entity = JCourse.builder()
            .id(model.getId())
            .ref(model.getRef())
            .title(model.getTitle())
            .credit(model.getCredit())
            .program(program)
            .semester(semester)
            .isActive(model.getIsActive())
            .build();

    JCourse saved = courseRepository.save(entity);
    return CourseMapper.toModel(saved);
  }
}