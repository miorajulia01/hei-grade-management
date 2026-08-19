package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JCourse;
import com.example.demo.entity.JExam;
import com.example.demo.entity.JGrade;
import com.example.demo.repository.GradeRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentProgressionServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private StudentProgressionService studentProgressionService;

    private JCourse course1;
    private JCourse course2;

    @BeforeEach
    void setUp() {
        course1 =
                JCourse.builder()
                        .id("course-1")
                        .ref("PROG1")
                        .title("Programming")
                        .credit(6)
                        .build();

        course2 =
                JCourse.builder()
                        .id("course-2")
                        .ref("WEB1")
                        .title("Web Development")
                        .credit(4)
                        .build();
    }

    @Test
    void shouldCalculateAverage() {
        JExam exam1 =
                JExam.builder()
                        .id("exam-1")
                        .course(course1)
                        .type("NORMAL")
                        .coefficient(0.4)
                        .order(1)
                        .build();

        JExam exam2 =
                JExam.builder()
                        .id("exam-2")
                        .course(course2)
                        .type("NORMAL")
                        .coefficient(0.6)
                        .order(2)
                        .build();

        JGrade grade1 =
                JGrade.builder()
                        .id("grade-1")
                        .exam(exam1)
                        .score(12.0)
                        .build();

        JGrade grade2 =
                JGrade.builder()
                        .id("grade-2")
                        .exam(exam2)
                        .score(8.0)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(grade1, grade2));

        assertEquals(
                9.6,
                studentProgressionService.calculateAverage("student-1"),
                0.001);
    }

    @Test
    void shouldReturnZeroWhenStudentHasNoGrades() {
        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of());

        assertEquals(
                0.0,
                studentProgressionService.calculateAverage("student-1"));
    }

    @Test
    void shouldBeRepeatingWhenAverageIsBelowTen() {
        JExam exam =
                JExam.builder()
                        .id("exam-1")
                        .course(course1)
                        .type("NORMAL")
                        .coefficient(1.0)
                        .order(1)
                        .build();

        JGrade grade =
                JGrade.builder()
                        .id("grade-1")
                        .exam(exam)
                        .score(9.0)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(grade));

        assertTrue(studentProgressionService.isRepeating("student-1"));
    }

    @Test
    void shouldNotBeRepeatingWhenAverageIsTen() {
        JExam exam =
                JExam.builder()
                        .id("exam-1")
                        .course(course1)
                        .type("NORMAL")
                        .coefficient(1.0)
                        .order(1)
                        .build();

        JGrade grade =
                JGrade.builder()
                        .id("grade-1")
                        .exam(exam)
                        .score(10.0)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(grade));

        assertFalse(studentProgressionService.isRepeating("student-1"));
    }

    @Test
    void shouldBeAdmittedWhenAverageIsAtLeastTen() {
        JExam exam =
                JExam.builder()
                        .id("exam-1")
                        .course(course1)
                        .type("NORMAL")
                        .coefficient(1.0)
                        .order(1)
                        .build();

        JGrade grade =
                JGrade.builder()
                        .id("grade-1")
                        .exam(exam)
                        .score(12.0)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(grade));

        assertTrue(studentProgressionService.isAdmitted("student-1"));
    }

    @Test
    void shouldCalculateValidatedCredits() {
        JExam exam1 =
                JExam.builder()
                        .id("exam-1")
                        .course(course1)
                        .type("NORMAL")
                        .coefficient(1.0)
                        .order(1)
                        .build();

        JExam exam2 =
                JExam.builder()
                        .id("exam-2")
                        .course(course2)
                        .type("NORMAL")
                        .coefficient(1.0)
                        .order(1)
                        .build();

        JGrade grade1 =
                JGrade.builder()
                        .id("grade-1")
                        .exam(exam1)
                        .score(14.0)
                        .build();

        JGrade grade2 =
                JGrade.builder()
                        .id("grade-2")
                        .exam(exam2)
                        .score(8.0)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(grade1, grade2));

        assertEquals(
                6,
                studentProgressionService.calculateValidatedCredits("student-1"));
    }

    @Test
    void shouldCountCourseCreditsOnlyOnce() {
        JExam exam1 =
                JExam.builder()
                        .id("exam-1")
                        .course(course1)
                        .type("NORMAL")
                        .coefficient(0.4)
                        .order(1)
                        .build();

        JExam exam2 =
                JExam.builder()
                        .id("exam-2")
                        .course(course1)
                        .type("NORMAL")
                        .coefficient(0.6)
                        .order(2)
                        .build();

        JGrade grade1 =
                JGrade.builder()
                        .id("grade-1")
                        .exam(exam1)
                        .score(12.0)
                        .build();

        JGrade grade2 =
                JGrade.builder()
                        .id("grade-2")
                        .exam(exam2)
                        .score(14.0)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(grade1, grade2));

        assertEquals(
                6,
                studentProgressionService.calculateValidatedCredits("student-1"));
    }

    @Test
    void shouldCapRetakeScoreAtTen() {
        JExam exam =
                JExam.builder()
                        .id("exam-retake")
                        .course(course1)
                        .type("RATTRAPAGE")
                        .coefficient(1.0)
                        .order(2)
                        .build();

        JGrade grade =
                JGrade.builder()
                        .id("grade-retake")
                        .exam(exam)
                        .score(18.0)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(grade));

        assertEquals(
                10.0,
                studentProgressionService.calculateAverage("student-1"),
                0.001);

        assertEquals(
                6,
                studentProgressionService.calculateValidatedCredits("student-1"));
    }

    @Test
    void shouldNotValidateCourseWhenRetakeScoreIsBelowTen() {
        JExam exam =
                JExam.builder()
                        .id("exam-retake")
                        .course(course1)
                        .type("RATTRAPAGE")
                        .coefficient(1.0)
                        .order(2)
                        .build();

        JGrade grade =
                JGrade.builder()
                        .id("grade-retake")
                        .exam(exam)
                        .score(8.0)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(grade));

        assertEquals(
                0,
                studentProgressionService.calculateValidatedCredits("student-1"));
    }

    @Test
    void shouldGraduateWith180CreditsAndAverageAtLeastTen() {
        JCourse course =
                JCourse.builder()
                        .id("course-180")
                        .credit(180)
                        .build();

        JExam exam =
                JExam.builder()
                        .id("exam-1")
                        .course(course)
                        .type("NORMAL")
                        .coefficient(1.0)
                        .order(1)
                        .build();

        JGrade grade =
                JGrade.builder()
                        .id("grade-1")
                        .exam(exam)
                        .score(10.0)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(grade));

        assertTrue(studentProgressionService.hasGraduated("student-1"));
    }

    @Test
    void shouldNotGraduateWithLessThan180Credits() {
        JCourse course =
                JCourse.builder()
                        .id("course-120")
                        .credit(120)
                        .build();

        JExam exam =
                JExam.builder()
                        .id("exam-1")
                        .course(course)
                        .type("NORMAL")
                        .coefficient(1.0)
                        .order(1)
                        .build();

        JGrade grade =
                JGrade.builder()
                        .id("grade-1")
                        .exam(exam)
                        .score(12.0)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(grade));

        assertFalse(studentProgressionService.hasGraduated("student-1"));
    }

    @Test
    void shouldNotGraduateWhenAverageIsBelowTen() {
        JCourse course =
                JCourse.builder()
                        .id("course-180")
                        .credit(180)
                        .build();

        JExam exam =
                JExam.builder()
                        .id("exam-1")
                        .course(course)
                        .type("NORMAL")
                        .coefficient(1.0)
                        .order(1)
                        .build();

        JGrade grade =
                JGrade.builder()
                        .id("grade-1")
                        .exam(exam)
                        .score(9.0)
                        .build();

        when(gradeRepository.findByStudentId("student-1"))
                .thenReturn(List.of(grade));

        assertFalse(studentProgressionService.hasGraduated("student-1"));
    }
}