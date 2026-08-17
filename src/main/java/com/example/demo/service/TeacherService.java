package com.example.demo.service;

import com.example.demo.entity.JTeacher;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.model.Teacher;
import com.example.demo.repository.TeacherRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherService {

  private final TeacherRepository teacherRepository;
  private final TeacherMapper teacherMapper;

  public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
    this.teacherRepository = teacherRepository;
    this.teacherMapper = teacherMapper;
  }

  @Transactional(readOnly = true)
  public List<Teacher> getTeachers(String teacherNumber) {
    List<JTeacher> entities;

    if (teacherNumber != null && !teacherNumber.isBlank()) {
      entities =
          teacherRepository.findByTeacherNumber(teacherNumber).map(List::of).orElse(List.of());
    } else {
      entities = teacherRepository.findAll();
    }

    return entities.stream().map(teacherMapper::toModel).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Teacher getTeacherById(String id) {
    return teacherRepository
        .findById(id)
        .map(teacherMapper::toModel)
        .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + id));
  }
}
