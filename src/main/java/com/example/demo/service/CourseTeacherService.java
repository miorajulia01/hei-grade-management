package com.example.demo.service;

import com.example.demo.entity.JCourse;
import com.example.demo.entity.JCourseTeacher;
import com.example.demo.entity.JTeacher;
import com.example.demo.mapper.CourseTeacherMapper;
import com.example.demo.model.CourseTeacher;
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
        return courseTeacherRepository.findAll().stream()
                .map(CourseTeacherMapper::toModel)
                .toList();
    }

    public CourseTeacher saveCourseTeacher(CourseTeacher model) {
        JCourse course = null;
        if (model.getCourse() != null && model.getCourse().getId() != null) {
            course = courseRepository.findById(model.getCourse().getId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
        }

        JTeacher teacher = null;
        if (model.getTeacher() != null && model.getTeacher().getId() != null) {
            teacher = teacherRepository.findById(model.getTeacher().getId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
        }

        JCourseTeacher entity = JCourseTeacher.builder()
                .course(course)
                .teacher(teacher)
                .isPrimary(model.getIsPrimary())
                .build();

        JCourseTeacher saved = courseTeacherRepository.save(entity);
        return CourseTeacherMapper.toModel(saved);
    }
}