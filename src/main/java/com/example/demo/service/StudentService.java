package com.example.demo.service;

import com.example.demo.entity.JPromotion;
import com.example.demo.entity.JStudent;
import com.example.demo.entity.JUser;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.model.Student;
import com.example.demo.repository.PromotionRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

  private final StudentRepository studentRepository;
  private final UserRepository userRepository;
  private final PromotionRepository promotionRepository;

  public List<Student> getAllStudents() {
    return studentRepository.findAll().stream().map(StudentMapper::toModel).toList();
  }

  public Student getStudentById(String id) {
    JStudent entity =
        studentRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    return StudentMapper.toModel(entity);
  }

  public Student saveStudent(Student model) {
    JUser user = null;
    if (model.getUser() != null && model.getUser().getId() != null) {
      user =
          userRepository
              .findById(model.getUser().getId())
              .orElseThrow(() -> new RuntimeException("User not found"));
    }

    JPromotion promotion = null;
    if (model.getPromotion() != null && model.getPromotion().getId() != null) {
      promotion =
          promotionRepository
              .findById(model.getPromotion().getId())
              .orElseThrow(() -> new RuntimeException("Promotion not found"));
    }

    JStudent entity =
        JStudent.builder()
            .id(model.getId())
            .user(user)
            .promotion(promotion)
            .studentNumber(model.getStudentNumber())
            .firstName(model.getFirstName())
            .lastName(model.getLastName())
            .email(model.getEmail())
            .status(model.getStatus())
            .dateEnroll(model.getDateEnroll())
            .build();

    JStudent saved = studentRepository.save(entity);
    return StudentMapper.toModel(saved);
  }

  public Student updateStudent(String id, Student model) {
    JStudent existing =
        studentRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

    if (model.getUser() != null && model.getUser().getId() != null) {
      JUser user =
          userRepository
              .findById(model.getUser().getId())
              .orElseThrow(() -> new RuntimeException("User not found"));
      existing.setUser(user);
    }
    if (model.getPromotion() != null && model.getPromotion().getId() != null) {
      JPromotion promotion =
          promotionRepository
              .findById(model.getPromotion().getId())
              .orElseThrow(() -> new RuntimeException("Promotion not found"));
      existing.setPromotion(promotion);
    }
    existing.setStudentNumber(model.getStudentNumber());
    existing.setFirstName(model.getFirstName());
    existing.setLastName(model.getLastName());
    existing.setEmail(model.getEmail());
    existing.setStatus(model.getStatus());
    existing.setDateEnroll(model.getDateEnroll());

    JStudent saved = studentRepository.save(existing);
    return StudentMapper.toModel(saved);
  }

  public void deleteStudent(String id) {
    if (!studentRepository.existsById(id)) {
      throw new RuntimeException("Student not found with id: " + id);
    }
    studentRepository.deleteById(id);
  }
}
