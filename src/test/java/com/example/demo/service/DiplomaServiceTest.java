package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JStudent;
import com.example.demo.model.Diploma;
import com.example.demo.repository.StudentRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DiplomaServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentProgressionService studentProgressionService;

    @InjectMocks
    private DiplomaService diplomaService;

    @Test
    void shouldReturnGraduateWhenStudentHas180CreditsAndAverageAtLeast10() {
        JStudent student =
                JStudent.builder()
                        .id("student-1")
                        .studentNumber("STD001")
                        .firstName("Jean")
                        .lastName("Rakoto")
                        .build();

        when(studentProgressionService.calculateAverage("student-1"))
                .thenReturn(12.5);

        when(studentProgressionService.calculateValidatedCredits("student-1"))
                .thenReturn(180);

        assertTrue(diplomaService.isGraduate("student-1"));
    }

    @Test
    void shouldNotGraduateWhenAverageIsBelow10() {
        when(studentProgressionService.calculateAverage("student-1"))
                .thenReturn(9.5);

        when(studentProgressionService.calculateValidatedCredits("student-1"))
                .thenReturn(180);

        assertFalse(diplomaService.isGraduate("student-1"));
    }

    @Test
    void shouldNotGraduateWhenCreditsAreBelow180() {
        when(studentProgressionService.calculateAverage("student-1"))
                .thenReturn(12.0);

        when(studentProgressionService.calculateValidatedCredits("student-1"))
                .thenReturn(175);

        assertFalse(diplomaService.isGraduate("student-1"));
    }

    @Test
    void shouldReturnGraduatesOrderedByAverage() {
        JStudent student1 =
                JStudent.builder()
                        .id("student-1")
                        .studentNumber("STD001")
                        .firstName("Jean")
                        .lastName("Rakoto")
                        .build();

        JStudent student2 =
                JStudent.builder()
                        .id("student-2")
                        .studentNumber("STD002")
                        .firstName("Paul")
                        .lastName("Rabe")
                        .build();

        when(studentRepository.findByPromotionId("promotion-1"))
                .thenReturn(List.of(student1, student2));

        when(studentProgressionService.calculateAverage("student-1"))
                .thenReturn(12.0);

        when(studentProgressionService.calculateAverage("student-2"))
                .thenReturn(15.0);

        when(studentProgressionService.calculateValidatedCredits("student-1"))
                .thenReturn(180);

        when(studentProgressionService.calculateValidatedCredits("student-2"))
                .thenReturn(180);

        List<Diploma> result =
                diplomaService.getGraduatesByPromotion("promotion-1");

        assertEquals(2, result.size());
        assertEquals("STD002", result.get(0).getStudentNumber());
        assertEquals(1, result.get(0).getRank());
        assertEquals("STD001", result.get(1).getStudentNumber());
        assertEquals(2, result.get(1).getRank());
    }
}