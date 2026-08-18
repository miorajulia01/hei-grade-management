package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JExam;
import com.example.demo.entity.JGrade;
import com.example.demo.entity.JStudent;
import com.example.demo.model.Grade;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.GradeRepository;
import com.example.demo.repository.StudentRepository;
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
class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ExamRepository examRepository;

    @InjectMocks
    private GradeService gradeService;

    private JGrade gradeEntity;
    private Grade gradeModel;

    @BeforeEach
    void setUp() {
        gradeEntity =
                JGrade.builder()
                        .id("grade-1")
                        .score(15.0)
                        .weightedScore(7.5)
                        .isValidated(true)
                        .build();

        gradeModel =
                Grade.builder()
                        .id("grade-1")
                        .score(15.0)
                        .weightedScore(7.5)
                        .build();
    }

    @Test
    void shouldGetAllGrades() {
        when(gradeRepository.findAll())
                .thenReturn(List.of(gradeEntity));

        List<Grade> result = gradeService.getAllGrades();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("grade-1", result.get(0).getId());
        assertEquals(15.0, result.get(0).getScore());

        verify(gradeRepository).findAll();
    }

    @Test
    void shouldGetGradeById() {
        when(gradeRepository.findById("grade-1"))
                .thenReturn(Optional.of(gradeEntity));

        Grade result = gradeService.getGradeById("grade-1");

        assertNotNull(result);
        assertEquals("grade-1", result.getId());
        assertEquals(15.0, result.getScore());

        verify(gradeRepository).findById("grade-1");
    }

    @Test
    void shouldThrowExceptionWhenGradeNotFound() {
        when(gradeRepository.findById("unknown"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> gradeService.getGradeById("unknown"));

        assertEquals(
                "Grade not found with id: unknown",
                exception.getMessage());

        verify(gradeRepository).findById("unknown");
    }

    @Test
    void shouldSaveGradeWithoutRelations() {
        when(gradeRepository.save(any(JGrade.class)))
                .thenReturn(gradeEntity);

        Grade result = gradeService.saveGrade(gradeModel);

        assertNotNull(result);
        assertEquals("grade-1", result.getId());
        assertEquals(15.0, result.getScore());

        ArgumentCaptor<JGrade> captor =
                ArgumentCaptor.forClass(JGrade.class);

        verify(gradeRepository).save(captor.capture());

        JGrade savedEntity = captor.getValue();

        assertEquals("grade-1", savedEntity.getId());
        assertEquals(15.0, savedEntity.getScore());
        assertEquals(7.5, savedEntity.getWeightedScore());
        assertTrue(savedEntity.getIsValidated());

        assertNull(savedEntity.getStudent());
        assertNull(savedEntity.getExam());
    }

    @Test
    void shouldSaveGradeWithStudent() {
        JStudent student =
                JStudent.builder()
                        .id("student-1")
                        .studentNumber("STD001")
                        .build();

        Grade model =
                Grade.builder()
                        .id("grade-1")
                        .score(15.0)
                        .weightedScore(7.5)
                        .student(
                                com.example.demo.model.Student.builder()
                                        .id("student-1")
                                        .build())
                        .build();

        when(studentRepository.findById("student-1"))
                .thenReturn(Optional.of(student));

        when(gradeRepository.save(any(JGrade.class)))
                .thenReturn(gradeEntity);

        gradeService.saveGrade(model);

        ArgumentCaptor<JGrade> captor =
                ArgumentCaptor.forClass(JGrade.class);

        verify(gradeRepository).save(captor.capture());

        assertEquals(student, captor.getValue().getStudent());

        verify(studentRepository).findById("student-1");
    }

    @Test
    void shouldSaveGradeWithExam() {
        JExam exam =
                JExam.builder()
                        .id("exam-1")
                        .coefficient(0.5)
                        .order(1)
                        .build();

        Grade model =
                Grade.builder()
                        .id("grade-1")
                        .score(15.0)
                        .weightedScore(7.5)
                        .exam(
                                com.example.demo.model.Exam.builder()
                                        .id("exam-1")
                                        .build())
                        .build();

        when(examRepository.findById("exam-1"))
                .thenReturn(Optional.of(exam));

        when(gradeRepository.save(any(JGrade.class)))
                .thenReturn(gradeEntity);

        gradeService.saveGrade(model);

        ArgumentCaptor<JGrade> captor =
                ArgumentCaptor.forClass(JGrade.class);

        verify(gradeRepository).save(captor.capture());

        assertEquals(exam, captor.getValue().getExam());

        verify(examRepository).findById("exam-1");
    }

    @Test
    void shouldSaveGradeWithStudentAndExam() {
        JStudent student =
                JStudent.builder()
                        .id("student-1")
                        .studentNumber("STD001")
                        .build();

        JExam exam =
                JExam.builder()
                        .id("exam-1")
                        .coefficient(0.5)
                        .order(1)
                        .build();

        Grade model =
                Grade.builder()
                        .id("grade-1")
                        .score(15.0)
                        .weightedScore(7.5)
                        .student(
                                com.example.demo.model.Student.builder()
                                        .id("student-1")
                                        .build())
                        .exam(
                                com.example.demo.model.Exam.builder()
                                        .id("exam-1")
                                        .build())
                        .build();

        when(studentRepository.findById("student-1"))
                .thenReturn(Optional.of(student));

        when(examRepository.findById("exam-1"))
                .thenReturn(Optional.of(exam));

        when(gradeRepository.save(any(JGrade.class)))
                .thenReturn(gradeEntity);

        gradeService.saveGrade(model);

        ArgumentCaptor<JGrade> captor =
                ArgumentCaptor.forClass(JGrade.class);

        verify(gradeRepository).save(captor.capture());

        JGrade savedEntity = captor.getValue();

        assertEquals(student, savedEntity.getStudent());
        assertEquals(exam, savedEntity.getExam());
        assertEquals(15.0, savedEntity.getScore());
        assertTrue(savedEntity.getIsValidated());

        verify(studentRepository).findById("student-1");
        verify(examRepository).findById("exam-1");
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        Grade model =
                Grade.builder()
                        .id("grade-1")
                        .score(15.0)
                        .student(
                                com.example.demo.model.Student.builder()
                                        .id("unknown")
                                        .build())
                        .build();

        when(studentRepository.findById("unknown"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> gradeService.saveGrade(model));

        assertEquals("Student not found", exception.getMessage());

        verify(studentRepository).findById("unknown");
        verify(gradeRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenExamNotFound() {
        Grade model =
                Grade.builder()
                        .id("grade-1")
                        .score(15.0)
                        .exam(
                                com.example.demo.model.Exam.builder()
                                        .id("unknown")
                                        .build())
                        .build();

        when(examRepository.findById("unknown"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> gradeService.saveGrade(model));

        assertEquals("Exam not found", exception.getMessage());

        verify(examRepository).findById("unknown");
        verify(gradeRepository, never()).save(any());
    }

    @Test
    void shouldRejectInvalidScore() {
        Grade model =
                Grade.builder()
                        .id("grade-1")
                        .score(25.0)
                        .build();

        assertThrows(
                RuntimeException.class,
                () -> gradeService.saveGrade(model));

        verify(gradeRepository, never()).save(any());
    }

    @Test
    void shouldCalculateWeightedAverage() {
        JExam exam1 =
                JExam.builder()
                        .id("exam-1")
                        .coefficient(0.4)
                        .build();

        JExam exam2 =
                JExam.builder()
                        .id("exam-2")
                        .coefficient(0.6)
                        .build();

        JGrade grade1 =
                JGrade.builder()
                        .id("grade-1")
                        .score(10.0)
                        .exam(exam1)
                        .build();

        JGrade grade2 =
                JGrade.builder()
                        .id("grade-2")
                        .score(16.0)
                        .exam(exam2)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(grade1, grade2));

        Double result =
                gradeService.calculateWeightedAverage("student-1");

        assertEquals(13.6, result, 0.001);

        verify(gradeRepository).findByStudentId("student-1");
    }

    @Test
    void shouldCalculateZeroWhenNoCoefficients() {
        JGrade grade =
                JGrade.builder()
                        .id("grade-1")
                        .score(15.0)
                        .exam(null)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(grade));

        Double result =
                gradeService.calculateWeightedAverage("student-1");

        assertEquals(0.0, result);

        verify(gradeRepository).findByStudentId("student-1");
    }

    @Test
    void shouldCalculateAverageIgnoringGradeWithoutExam() {
        JExam exam =
                JExam.builder()
                        .id("exam-1")
                        .coefficient(0.5)
                        .build();

        JGrade gradeWithExam =
                JGrade.builder()
                        .id("grade-1")
                        .score(14.0)
                        .exam(exam)
                        .build();

        JGrade gradeWithoutExam =
                JGrade.builder()
                        .id("grade-2")
                        .score(20.0)
                        .exam(null)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(gradeWithExam, gradeWithoutExam));

        Double result =
                gradeService.calculateWeightedAverage("student-1");

        assertEquals(14.0, result, 0.001);
    }

    @Test
    void shouldGetRetakeGradesForStudent() {
        JGrade normalGrade =
                JGrade.builder()
                        .id("grade-1")
                        .score(15.0)
                        .build();

        JGrade retakeGrade =
                JGrade.builder()
                        .id("grade-2")
                        .score(5.0)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(normalGrade, retakeGrade));

        List<Grade> result =
                gradeService.getRetakeGradesForStudent("student-1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("grade-2", result.get(0).getId());
        assertEquals(5.0, result.get(0).getScore());

        verify(gradeRepository).findByStudentId("student-1");
    }

    @Test
    void shouldReturnEmptyListWhenStudentHasNoRetake() {
        JGrade grade =
                JGrade.builder()
                        .id("grade-1")
                        .score(15.0)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(grade));

        List<Grade> result =
                gradeService.getRetakeGradesForStudent("student-1");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}