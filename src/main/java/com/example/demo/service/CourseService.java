package com.example.demo.service;

import com.example.demo.entity.JCourse;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Transactional(readOnly = true)
    public List<Course> getCourses(String code, String name) {
        List<JCourse> entities;

        if (code != null && !code.isBlank()) {
            entities = courseRepository.findByCode(code)
                    .map(List::of)
                    .orElse(List.of());
        } else if (name != null && !name.isBlank()) {
            entities = courseRepository.findByNameContainingIgnoreCase(name);
        } else {
            entities = courseRepository.findAll();
        }

        return entities.stream()
                .map(courseMapper::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Course getCourseById(String id) {
        return courseRepository.findById(id)
                .map(courseMapper::toModel)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + id));
    }
}