package com.example.demo.service;

import com.example.demo.entity.JTeacher;
import com.example.demo.entity.JUser;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.model.Teacher;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

  private final TeacherRepository teacherRepository;
  private final UserRepository userRepository;

  public List<Teacher> getAllTeachers() {
    return teacherRepository.findAll().stream().map(TeacherMapper::toModel).toList();
  }

  public Teacher getTeacherById(String id) {
    JTeacher entity =
        teacherRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
    return TeacherMapper.toModel(entity);
  }

  public Teacher saveTeacher(Teacher model) {
    JUser user = null;
    if (model.getUser() != null && model.getUser().getId() != null) {
      user =
          userRepository
              .findById(model.getUser().getId())
              .orElseThrow(() -> new RuntimeException("User not found"));
    }

    JTeacher entity =
        JTeacher.builder()
            .id(model.getId())
            .user(user)
            .firstName(model.getFirstName())
            .lastName(model.getLastName())
            .specialty(model.getSpecialty())
            .status(model.getStatus())
            .build();

    JTeacher saved = teacherRepository.save(entity);
    return TeacherMapper.toModel(saved);
  }

  public Teacher updateTeacher(String id, Teacher model) {
    JTeacher existing =
        teacherRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));

    if (model.getUser() != null && model.getUser().getId() != null) {
      JUser user =
          userRepository
              .findById(model.getUser().getId())
              .orElseThrow(() -> new RuntimeException("User not found"));
      existing.setUser(user);
    }
    existing.setFirstName(model.getFirstName());
    existing.setLastName(model.getLastName());
    existing.setSpecialty(model.getSpecialty());
    existing.setStatus(model.getStatus());

    JTeacher saved = teacherRepository.save(existing);
    return TeacherMapper.toModel(saved);
  }

  public void deleteTeacher(String id) {
    if (!teacherRepository.existsById(id)) {
      throw new RuntimeException("Teacher not found with id: " + id);
    }
    teacherRepository.deleteById(id);
  }
}
