package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JCourse;
import com.example.demo.entity.JExam;
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

    @Mock
    private ExamRepository examRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private ExamService examService;

    private JExam examEntity;
    private Exam examModel;

    @BeforeEach
    void setUp() {
        examEntity =
                JExam.builder()
                        .id("exam-1")
                        .title("Final Exam")
                        .coefficient(0.5)
                        .order(1)
                        .isPublished(true)
                        .build();

        examModel =
                Exam.builder()
                        .id("exam-1")
                        .title("Final Exam")
                        .coefficient(0.5)
                        .order(1)
                        .isPublished(true)
                        .build();
    }

    @Test
    void shouldGetAllExams() {
        when(examRepository.findAll())
                .thenReturn(List.of(examEntity));

        List<Exam> result = examService.getAllExams();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("exam-1", result.get(0).getId());
        assertEquals("Final Exam", result.get(0).getTitle());
        assertEquals(0.5, result.get(0).getCoefficient());
        assertEquals(1, result.get(0).getOrder());
        assertTrue(result.get(0).getIsPublished());

        verify(examRepository).findAll();
    }

    @Test
    void shouldGetExamById() {
        when(examRepository.findById("exam-1"))
                .thenReturn(Optional.of(examEntity));

        Exam result = examService.getExamById("exam-1");

        assertNotNull(result);
        assertEquals("exam-1", result.getId());
        assertEquals("Final Exam", result.getTitle());

        verify(examRepository).findById("exam-1");
    }

    @Test
    void shouldThrowExceptionWhenExamNotFound() {
        when(examRepository.findById("unknown"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> examService.getExamById("unknown"));

        assertEquals(
                "Exam not found with id: unknown",
                exception.getMessage());

        verify(examRepository).findById("unknown");
    }

    @Test
    void shouldSaveExamWithoutCourse() {
        when(examRepository.save(any(JExam.class)))
                .thenReturn(examEntity);

        Exam result = examService.saveExam(examModel);

        assertNotNull(result);
        assertEquals("exam-1", result.getId());
        assertEquals("Final Exam", result.getTitle());
        assertEquals(0.5, result.getCoefficient());
        assertEquals(1, result.getOrder());

        ArgumentCaptor<JExam> captor =
                ArgumentCaptor.forClass(JExam.class);

        verify(examRepository).save(captor.capture());

        JExam savedEntity = captor.getValue();

        assertEquals("exam-1", savedEntity.getId());
        assertEquals("Final Exam", savedEntity.getTitle());
        assertEquals(0.5, savedEntity.getCoefficient());
        assertEquals(1, savedEntity.getOrder());
        assertNull(savedEntity.getCourse());
    }

    @Test
    void shouldSaveExamWithCourse() {
        JCourse course =
                JCourse.builder()
                        .id("course-1")
                        .ref("JAVA")
                        .title("Java Programming")
                        .build();

        Exam model =
                Exam.builder()
                        .id("exam-1")
                        .title("Final Exam")
                        .coefficient(0.5)
                        .order(1)
                        .isPublished(true)
                        .course(
                                com.example.demo.model.Course.builder()
                                        .id("course-1")
                                        .build())
                        .build();

        when(courseRepository.findById("course-1"))
                .thenReturn(Optional.of(course));

        when(examRepository.save(any(JExam.class)))
                .thenReturn(examEntity);

        examService.saveExam(model);

        ArgumentCaptor<JExam> captor =
                ArgumentCaptor.forClass(JExam.class);

        verify(examRepository).save(captor.capture());

        JExam savedEntity = captor.getValue();

        assertEquals(course, savedEntity.getCourse());

        verify(courseRepository).findById("course-1");
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {
        Exam model =
                Exam.builder()
                        .id("exam-1")
                        .title("Final Exam")
                        .coefficient(0.5)
                        .order(1)
                        .isPublished(true)
                        .course(
                                com.example.demo.model.Course.builder()
                                        .id("unknown")
                                        .build())
                        .build();

        when(courseRepository.findById("unknown"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> examService.saveExam(model));

        assertEquals("Course not found", exception.getMessage());

        verify(courseRepository).findById("unknown");
        verify(examRepository, never()).save(any());
    }

    @Test
    void shouldRejectExamWithInvalidCoefficient() {
        Exam model =
                Exam.builder()
                        .id("exam-1")
                        .title("Invalid Exam")
                        .coefficient(0.0)
                        .order(1)
                        .isPublished(true)
                        .build();

        assertThrows(
                RuntimeException.class,
                () -> examService.saveExam(model));

        verify(examRepository, never()).save(any());
    }

    @Test
    void shouldRejectExamWithInvalidOrder() {
        Exam model =
                Exam.builder()
                        .id("exam-1")
                        .title("Invalid Exam")
                        .coefficient(0.5)
                        .order(0)
                        .isPublished(true)
                        .build();

        assertThrows(
                RuntimeException.class,
                () -> examService.saveExam(model));

        verify(examRepository, never()).save(any());
    }
}