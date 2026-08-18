package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JCourse;
import com.example.demo.entity.JExam;
import com.example.demo.model.Course;
import com.example.demo.model.Exam;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.ExamRepository;
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
class ExamServiceTest {

  @Mock private ExamRepository examRepository;

  @Mock private CourseRepository courseRepository;

  @InjectMocks private ExamService examService;

  private JCourse courseEntity;
  private Course courseModel;
  private JExam examEntity;
  private Exam examModel;

  @BeforeEach
  void setUp() {
    courseEntity = JCourse.builder().id("course-1").ref("JAVA").title("Java Programming").build();

    courseModel = Course.builder().id("course-1").ref("JAVA").title("Java Programming").build();

    examEntity =
        JExam.builder()
            .id("exam-1")
            .course(courseEntity)
            .type("FINAL")
            .title("Final Exam")
            .coefficient(0.5)
            .order(1)
            .isPublished(true)
            .build();

    examModel =
        Exam.builder()
            .id("exam-1")
            .course(courseModel)
            .type("FINAL")
            .title("Final Exam")
            .coefficient(0.5)
            .order(1)
            .isPublished(true)
            .build();
  }

  @Test
  void shouldGetAllExams() {
    when(examRepository.findAll()).thenReturn(List.of(examEntity));

    List<Exam> result = examService.getAllExams();

    assertNotNull(result);
    assertEquals(1, result.size());

    assertEquals("exam-1", result.get(0).getId());
    assertEquals("FINAL", result.get(0).getType());
    assertEquals("Final Exam", result.get(0).getTitle());
    assertEquals(0.5, result.get(0).getCoefficient());
    assertEquals(1, result.get(0).getOrder());
    assertTrue(result.get(0).getIsPublished());

    verify(examRepository).findAll();
  }

  @Test
  void shouldGetExamById() {
    when(examRepository.findById("exam-1")).thenReturn(Optional.of(examEntity));

    Exam result = examService.getExamById("exam-1");

    assertNotNull(result);
    assertEquals("exam-1", result.getId());
    assertEquals("FINAL", result.getType());
    assertEquals("Final Exam", result.getTitle());

    verify(examRepository).findById("exam-1");
  }

  @Test
  void shouldThrowExceptionWhenExamNotFound() {
    when(examRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> examService.getExamById("unknown"));

    assertEquals("Exam not found with id: unknown", exception.getMessage());

    verify(examRepository).findById("unknown");
  }

  @Test
  void shouldSaveExamWithCourse() {
    when(courseRepository.findById("course-1")).thenReturn(Optional.of(courseEntity));

    when(examRepository.save(any(JExam.class))).thenReturn(examEntity);

    Exam result = examService.saveExam(examModel);

    assertNotNull(result);
    assertEquals("exam-1", result.getId());
    assertEquals("FINAL", result.getType());
    assertEquals("Final Exam", result.getTitle());
    assertEquals(0.5, result.getCoefficient());
    assertEquals(1, result.getOrder());

    ArgumentCaptor<JExam> captor = ArgumentCaptor.forClass(JExam.class);

    verify(examRepository).save(captor.capture());

    JExam savedEntity = captor.getValue();

    assertEquals("exam-1", savedEntity.getId());
    assertEquals(courseEntity, savedEntity.getCourse());
    assertEquals("FINAL", savedEntity.getType());
    assertEquals("Final Exam", savedEntity.getTitle());
    assertEquals(0.5, savedEntity.getCoefficient());
    assertEquals(1, savedEntity.getOrder());
    assertTrue(savedEntity.getIsPublished());

    verify(courseRepository).findById("course-1");
  }

  @Test
  void shouldThrowExceptionWhenCourseNotFound() {
    when(courseRepository.findById("unknown")).thenReturn(Optional.empty());

    Exam model =
        Exam.builder()
            .id("exam-1")
            .course(Course.builder().id("unknown").build())
            .type("FINAL")
            .title("Final Exam")
            .coefficient(0.5)
            .order(1)
            .isPublished(true)
            .build();

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> examService.saveExam(model));

    assertEquals("Course not found", exception.getMessage());

    verify(courseRepository).findById("unknown");
    verify(examRepository, never()).save(any());
  }

  @Test
  void shouldRejectExamWithNullCourse() {
    Exam model =
        Exam.builder()
            .id("exam-1")
            .type("FINAL")
            .title("Final Exam")
            .coefficient(0.5)
            .order(1)
            .isPublished(true)
            .build();

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> examService.saveExam(model));

    assertEquals("Exam must be associated with a course", exception.getMessage());

    verifyNoInteractions(courseRepository);
    verifyNoInteractions(examRepository);
  }

  @Test
  void shouldRejectExamWithEmptyType() {
    Exam model =
        Exam.builder()
            .id("exam-1")
            .course(courseModel)
            .type("")
            .title("Final Exam")
            .coefficient(0.5)
            .order(1)
            .isPublished(true)
            .build();

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> examService.saveExam(model));

    assertEquals("Exam type cannot be empty", exception.getMessage());

    verifyNoInteractions(courseRepository);
    verifyNoInteractions(examRepository);
  }

  @Test
  void shouldRejectExamWithInvalidCoefficient() {
    Exam model =
        Exam.builder()
            .id("exam-1")
            .course(courseModel)
            .type("FINAL")
            .title("Final Exam")
            .coefficient(0.0)
            .order(1)
            .isPublished(true)
            .build();

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> examService.saveExam(model));

    assertEquals(
        "Coefficient must be greater than 0 and less than or equal to 1", exception.getMessage());

    verifyNoInteractions(courseRepository);
    verifyNoInteractions(examRepository);
  }

  @Test
  void shouldRejectExamWithInvalidOrder() {
    Exam model =
        Exam.builder()
            .id("exam-1")
            .course(courseModel)
            .type("FINAL")
            .title("Final Exam")
            .coefficient(0.5)
            .order(0)
            .isPublished(true)
            .build();

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> examService.saveExam(model));

    assertEquals("Exam order must be greater than or equal to 1", exception.getMessage());

    verifyNoInteractions(courseRepository);
    verifyNoInteractions(examRepository);
  }
}
