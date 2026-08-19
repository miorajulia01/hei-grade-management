package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JTeacher;
import com.example.demo.entity.JUser;
import com.example.demo.enums.StatusEnum;
import com.example.demo.enums.UserRole;
import com.example.demo.model.Teacher;
import com.example.demo.model.User;
import com.example.demo.repository.TeacherRepository;
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
class TeacherServiceTest {

  @Mock private TeacherRepository teacherRepository;

  @Mock private UserRepository userRepository;

  @InjectMocks private TeacherService teacherService;

  private JTeacher teacherEntity;

  @BeforeEach
  void setUp() {
    teacherEntity =
        JTeacher.builder()
            .id("teacher-1")
            .firstName("Jean")
            .lastName("Rakoto")
            .specialty("Programming")
            .status(StatusEnum.ACTIVE)
            .build();
  }

  @Test
  void shouldGetAllTeachers() {
    when(teacherRepository.findAll()).thenReturn(List.of(teacherEntity));

    List<Teacher> result = teacherService.getAllTeachers();

    assertEquals(1, result.size());
    assertEquals("teacher-1", result.get(0).getId());
    assertEquals("Jean", result.get(0).getFirstName());
    assertEquals("Rakoto", result.get(0).getLastName());

    verify(teacherRepository).findAll();
  }

  @Test
  void shouldGetTeacherById() {
    when(teacherRepository.findById("teacher-1")).thenReturn(Optional.of(teacherEntity));

    Teacher result = teacherService.getTeacherById("teacher-1");

    assertNotNull(result);
    assertEquals("teacher-1", result.getId());
    assertEquals("Jean", result.getFirstName());
    assertEquals("Rakoto", result.getLastName());
    assertEquals("Programming", result.getSpecialty());

    verify(teacherRepository).findById("teacher-1");
  }

  @Test
  void shouldThrowExceptionWhenTeacherNotFound() {
    when(teacherRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> teacherService.getTeacherById("unknown"));

    assertEquals("Teacher not found with id: unknown", exception.getMessage());

    verify(teacherRepository).findById("unknown");
  }

  @Test
  void shouldSaveTeacherWithoutUser() {
    Teacher model =
        Teacher.builder()
            .id("teacher-1")
            .firstName("Jean")
            .lastName("Rakoto")
            .specialty("Programming")
            .status(StatusEnum.ACTIVE)
            .build();

    when(teacherRepository.save(any(JTeacher.class))).thenReturn(teacherEntity);

    Teacher result = teacherService.saveTeacher(model);

    assertNotNull(result);
    assertEquals("teacher-1", result.getId());
    assertEquals("Jean", result.getFirstName());
    assertEquals("Rakoto", result.getLastName());
    assertEquals("Programming", result.getSpecialty());

    verify(teacherRepository).save(any(JTeacher.class));
    verifyNoInteractions(userRepository);
  }

  @Test
  void shouldSaveTeacherWithUser() {
    JUser userEntity =
        JUser.builder()
            .id("user-1")
            .email("teacher@hei.school")
            .role(UserRole.TEACHER)
            .status(StatusEnum.ACTIVE)
            .build();

    User userModel =
        User.builder()
            .id("user-1")
            .email("teacher@hei.school")
            .role(UserRole.TEACHER)
            .status(StatusEnum.ACTIVE)
            .build();

    Teacher model =
        Teacher.builder()
            .id("teacher-1")
            .user(userModel)
            .firstName("Jean")
            .lastName("Rakoto")
            .specialty("Programming")
            .status(StatusEnum.ACTIVE)
            .build();

    JTeacher savedTeacher =
        JTeacher.builder()
            .id("teacher-1")
            .user(userEntity)
            .firstName("Jean")
            .lastName("Rakoto")
            .specialty("Programming")
            .status(StatusEnum.ACTIVE)
            .build();

    when(userRepository.findById("user-1")).thenReturn(Optional.of(userEntity));

    when(teacherRepository.save(any(JTeacher.class))).thenReturn(savedTeacher);

    Teacher result = teacherService.saveTeacher(model);

    assertNotNull(result);
    assertEquals("teacher-1", result.getId());
    assertNotNull(result.getUser());
    assertEquals("user-1", result.getUser().getId());

    verify(userRepository).findById("user-1");
    verify(teacherRepository).save(any(JTeacher.class));
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {
    User userModel =
        User.builder()
            .id("unknown-user")
            .email("unknown@hei.school")
            .role(UserRole.TEACHER)
            .status(StatusEnum.ACTIVE)
            .build();

    Teacher model =
        Teacher.builder()
            .id("teacher-1")
            .user(userModel)
            .firstName("Jean")
            .lastName("Rakoto")
            .specialty("Programming")
            .status(StatusEnum.ACTIVE)
            .build();

    when(userRepository.findById("unknown-user")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> teacherService.saveTeacher(model));

    assertEquals("User not found", exception.getMessage());

    verify(userRepository).findById("unknown-user");
    verify(teacherRepository, never()).save(any(JTeacher.class));
  }

  @Test
  void shouldSaveTeacherWithCorrectData() {
    Teacher model =
        Teacher.builder()
            .id("teacher-1")
            .firstName("Jean")
            .lastName("Rakoto")
            .specialty("Artificial Intelligence")
            .status(StatusEnum.ACTIVE)
            .build();

    when(teacherRepository.save(any(JTeacher.class))).thenReturn(teacherEntity);

    teacherService.saveTeacher(model);

    ArgumentCaptor<JTeacher> captor = ArgumentCaptor.forClass(JTeacher.class);

    verify(teacherRepository).save(captor.capture());

    JTeacher saved = captor.getValue();

    assertEquals("teacher-1", saved.getId());
    assertEquals("Jean", saved.getFirstName());
    assertEquals("Rakoto", saved.getLastName());
    assertEquals("Artificial Intelligence", saved.getSpecialty());
    assertEquals(StatusEnum.ACTIVE, saved.getStatus());
    assertNull(saved.getUser());
  }
}
