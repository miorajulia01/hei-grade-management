package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JStudent;
import com.example.demo.entity.JUser;
import com.example.demo.model.Student;
import com.example.demo.repository.PromotionRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock private StudentRepository studentRepository;

  @Mock private UserRepository userRepository;

  @Mock private PromotionRepository promotionRepository;

  @InjectMocks private StudentService studentService;

  private JStudent studentEntity;
  private Student studentModel;

  @BeforeEach
  void setUp() {
    studentEntity = JStudent.builder().id("student-1").studentNumber("STD001").build();

    studentModel = Student.builder().id("student-1").studentNumber("STD001").build();
  }

  @Test
  void shouldGetAllStudents() {
    when(studentRepository.findAll()).thenReturn(List.of(studentEntity));

    List<Student> result = studentService.getAllStudents();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("student-1", result.get(0).getId());
    assertEquals("STD001", result.get(0).getStudentNumber());

    verify(studentRepository).findAll();
  }

  @Test
  void shouldGetStudentById() {
    when(studentRepository.findById("student-1")).thenReturn(Optional.of(studentEntity));

    Student result = studentService.getStudentById("student-1");

    assertNotNull(result);
    assertEquals("student-1", result.getId());
    assertEquals("STD001", result.getStudentNumber());

    verify(studentRepository).findById("student-1");
  }

  @Test
  void shouldThrowExceptionWhenStudentNotFound() {
    when(studentRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> studentService.getStudentById("unknown"));

    assertEquals("Student not found with id: unknown", exception.getMessage());

    verify(studentRepository).findById("unknown");
  }

  @Test
  void shouldSaveStudentWithoutRelations() {
    when(studentRepository.save(any(JStudent.class))).thenReturn(studentEntity);

    Student result = studentService.saveStudent(studentModel);

    assertNotNull(result);
    assertEquals("student-1", result.getId());
    assertEquals("STD001", result.getStudentNumber());

    ArgumentCaptor<JStudent> captor = ArgumentCaptor.forClass(JStudent.class);

    verify(studentRepository).save(captor.capture());

    JStudent savedEntity = captor.getValue();

    assertEquals("student-1", savedEntity.getId());
    assertEquals("STD001", savedEntity.getStudentNumber());
    assertNull(savedEntity.getUser());
    assertNull(savedEntity.getPromotion());
  }

  @Test
  void shouldSaveStudentWithUser() {
    JUser user = JUser.builder().id("user-1").email("student@example.com").build();

    Student model =
        Student.builder()
            .id("student-1")
            .studentNumber("STD001")
            .user(
                com.example.demo.model.User.builder()
                    .id("user-1")
                    .email("student@example.com")
                    .build())
            .build();

    when(userRepository.findById("user-1")).thenReturn(Optional.of(user));

    when(studentRepository.save(any(JStudent.class))).thenReturn(studentEntity);

    studentService.saveStudent(model);

    ArgumentCaptor<JStudent> captor = ArgumentCaptor.forClass(JStudent.class);

    verify(studentRepository).save(captor.capture());

    assertEquals(user, captor.getValue().getUser());
    verify(userRepository).findById("user-1");
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {
    Student model =
        Student.builder()
            .id("student-1")
            .studentNumber("STD001")
            .user(com.example.demo.model.User.builder().id("unknown").build())
            .build();

    when(userRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> studentService.saveStudent(model));

    assertEquals("User not found", exception.getMessage());

    verify(userRepository).findById("unknown");
    verify(studentRepository, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenPromotionNotFound() {
    com.example.demo.model.Promotion promotion =
        com.example.demo.model.Promotion.builder().id("promotion-1").build();

    Student model =
        Student.builder().id("student-1").studentNumber("STD001").promotion(promotion).build();

    when(promotionRepository.findById("promotion-1")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> studentService.saveStudent(model));

    assertEquals("Promotion not found", exception.getMessage());

    verify(promotionRepository).findById("promotion-1");
    verify(studentRepository, never()).save(any());
  }
}
