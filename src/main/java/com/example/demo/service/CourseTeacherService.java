package com.example.demo.service;

import com.example.demo.entity.JCourse;
import com.example.demo.entity.JCourseTeacher;
import com.example.demo.entity.JTeacher;
import com.example.demo.mapper.CourseTeacherMapper;
import com.example.demo.model.CourseTeacher;
import com.example.demo.model.CourseTeacherId;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.CourseTeacherRepository;
import com.example.demo.repository.TeacherRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseTeacherService {

  private final CourseTeacherRepository courseTeacherRepository;
  private final CourseRepository courseRepository;
  private final TeacherRepository teacherRepository;

  public List<CourseTeacher> getAllCourseTeachers() {
    return courseTeacherRepository.findAll().stream().map(CourseTeacherMapper::toModel).toList();
  }

  public CourseTeacher saveCourseTeacher(CourseTeacher model) {
    JCourse course = null;
    if (model.getCourse() != null && model.getCourse().getId() != null) {
      course =
          courseRepository
              .findById(model.getCourse().getId())
              .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    JTeacher teacher = null;
    if (model.getTeacher() != null && model.getTeacher().getId() != null) {
      teacher =
          teacherRepository
              .findById(model.getTeacher().getId())
              .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    JCourseTeacher entity =
        JCourseTeacher.builder()
            .course(course)
            .teacher(teacher)
            .isPrimary(model.getIsPrimary())
            .build();

    JCourseTeacher saved = courseTeacherRepository.save(entity);
    return CourseTeacherMapper.toModel(saved);
  }

  public CourseTeacher getCourseTeacherById(String courseId, String teacherId) {
    JCourseTeacher entity =
        courseTeacherRepository
            .findById(new CourseTeacherId(courseId, teacherId))
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "CourseTeacher not found with courseId: "
                            + courseId
                            + " and teacherId: "
                            + teacherId));
    return CourseTeacherMapper.toModel(entity);
  }

  public CourseTeacher updateCourseTeacher(String courseId, String teacherId, CourseTeacher model) {
    JCourseTeacher existing =
        courseTeacherRepository
            .findById(new CourseTeacherId(courseId, teacherId))
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "CourseTeacher not found with courseId: "
                            + courseId
                            + " and teacherId: "
                            + teacherId));

    if (model.getAssignedAt() != null) {
      existing.setAssignedAt(model.getAssignedAt());
    }
    if (model.getIsPrimary() != null) {
      existing.setIsPrimary(model.getIsPrimary());
    }

    JCourseTeacher saved = courseTeacherRepository.save(existing);
    return CourseTeacherMapper.toModel(saved);
  }

  public void deleteCourseTeacher(String courseId, String teacherId) {
    CourseTeacherId id = new CourseTeacherId(courseId, teacherId);
    if (!courseTeacherRepository.existsById(id)) {
      throw new RuntimeException(
          "CourseTeacher not found with courseId: " + courseId + " and teacherId: " + teacherId);
    }
    courseTeacherRepository.deleteById(id);
  }
}
