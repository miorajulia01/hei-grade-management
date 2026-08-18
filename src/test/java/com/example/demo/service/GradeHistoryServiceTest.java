package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JGrade;
import com.example.demo.entity.JGradeHistory;
import com.example.demo.entity.JTeacher;
import com.example.demo.model.GradeHistory;
import com.example.demo.repository.GradeHistoryRepository;
import com.example.demo.repository.GradeRepository;
import com.example.demo.repository.TeacherRepository;
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
class GradeHistoryServiceTest {

    @Mock
    private GradeHistoryRepository gradeHistoryRepository;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private GradeHistoryService gradeHistoryService;

    private JGradeHistory historyEntity;
    private GradeHistory historyModel;

    @BeforeEach
    void setUp() {
        historyEntity =
                JGradeHistory.builder()
                        .id("history-1")
                        .oldScore(10.0)
                        .newScore(15.0)
                        .reason("Correction de la note")
                        .build();

        historyModel =
                GradeHistory.builder()
                        .id("history-1")
                        .oldScore(10.0)
                        .newScore(15.0)
                        .reason("Correction de la note")
                        .build();
    }

    @Test
    void shouldGetAllGradeHistories() {
        when(gradeHistoryRepository.findAll())
                .thenReturn(List.of(historyEntity));

        List<GradeHistory> result =
                gradeHistoryService.getAllGradeHistories();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("history-1", result.get(0).getId());
        assertEquals(10.0, result.get(0).getOldScore());
        assertEquals(15.0, result.get(0).getNewScore());
        assertEquals(
                "Correction de la note",
                result.get(0).getReason());

        verify(gradeHistoryRepository).findAll();
    }

    @Test
    void shouldGetGradeHistoryById() {
        when(gradeHistoryRepository.findById("history-1"))
                .thenReturn(Optional.of(historyEntity));

        GradeHistory result =
                gradeHistoryService.getGradeHistoryById("history-1");

        assertNotNull(result);
        assertEquals("history-1", result.getId());
        assertEquals(10.0, result.getOldScore());
        assertEquals(15.0, result.getNewScore());

        verify(gradeHistoryRepository).findById("history-1");
    }

    @Test
    void shouldThrowExceptionWhenGradeHistoryNotFound() {
        when(gradeHistoryRepository.findById("unknown"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () ->
                                gradeHistoryService.getGradeHistoryById(
                                        "unknown"));

        assertEquals(
                "GradeHistory not found with id: unknown",
                exception.getMessage());

        verify(gradeHistoryRepository).findById("unknown");
    }

    @Test
    void shouldSaveGradeHistoryWithoutRelations() {
        when(gradeHistoryRepository.save(any(JGradeHistory.class)))
                .thenReturn(historyEntity);

        GradeHistory result =
                gradeHistoryService.saveGradeHistory(historyModel);

        assertNotNull(result);
        assertEquals("history-1", result.getId());
        assertEquals(10.0, result.getOldScore());
        assertEquals(15.0, result.getNewScore());
        assertEquals(
                "Correction de la note",
                result.getReason());

        ArgumentCaptor<JGradeHistory> captor =
                ArgumentCaptor.forClass(JGradeHistory.class);

        verify(gradeHistoryRepository).save(captor.capture());

        JGradeHistory savedEntity = captor.getValue();

        assertEquals("history-1", savedEntity.getId());
        assertEquals(10.0, savedEntity.getOldScore());
        assertEquals(15.0, savedEntity.getNewScore());
        assertEquals(
                "Correction de la note",
                savedEntity.getReason());

        assertNull(savedEntity.getGrade());
        assertNull(savedEntity.getTeacher());
    }

    @Test
    void shouldSaveGradeHistoryWithGrade() {
        JGrade grade =
                JGrade.builder()
                        .id("grade-1")
                        .score(10.0)
                        .build();

        GradeHistory model =
                GradeHistory.builder()
                        .id("history-1")
                        .oldScore(10.0)
                        .newScore(15.0)
                        .reason("Correction de la note")
                        .grade(
                                com.example.demo.model.Grade.builder()
                                        .id("grade-1")
                                        .score(10.0)
                                        .build())
                        .build();

        when(gradeRepository.findById("grade-1"))
                .thenReturn(Optional.of(grade));

        when(gradeHistoryRepository.save(any(JGradeHistory.class)))
                .thenReturn(historyEntity);

        gradeHistoryService.saveGradeHistory(model);

        ArgumentCaptor<JGradeHistory> captor =
                ArgumentCaptor.forClass(JGradeHistory.class);

        verify(gradeHistoryRepository).save(captor.capture());

        assertEquals(
                grade,
                captor.getValue().getGrade());

        verify(gradeRepository).findById("grade-1");
    }

    @Test
    void shouldSaveGradeHistoryWithTeacher() {
        JTeacher teacher =
                JTeacher.builder()
                        .id("teacher-1")
                        .build();

        GradeHistory model =
                GradeHistory.builder()
                        .id("history-1")
                        .oldScore(10.0)
                        .newScore(15.0)
                        .reason("Correction de la note")
                        .teacher(
                                com.example.demo.model.Teacher.builder()
                                        .id("teacher-1")
                                        .build())
                        .build();

        when(teacherRepository.findById("teacher-1"))
                .thenReturn(Optional.of(teacher));

        when(gradeHistoryRepository.save(any(JGradeHistory.class)))
                .thenReturn(historyEntity);

        gradeHistoryService.saveGradeHistory(model);

        ArgumentCaptor<JGradeHistory> captor =
                ArgumentCaptor.forClass(JGradeHistory.class);

        verify(gradeHistoryRepository).save(captor.capture());

        assertEquals(
                teacher,
                captor.getValue().getTeacher());

        verify(teacherRepository).findById("teacher-1");
    }

    @Test
    void shouldSaveGradeHistoryWithGradeAndTeacher() {
        JGrade grade =
                JGrade.builder()
                        .id("grade-1")
                        .score(10.0)
                        .build();

        JTeacher teacher =
                JTeacher.builder()
                        .id("teacher-1")
                        .build();

        GradeHistory model =
                GradeHistory.builder()
                        .id("history-1")
                        .oldScore(10.0)
                        .newScore(15.0)
                        .reason("Correction de la note")
                        .grade(
                                com.example.demo.model.Grade.builder()
                                        .id("grade-1")
                                        .score(10.0)
                                        .build())
                        .teacher(
                                com.example.demo.model.Teacher.builder()
                                        .id("teacher-1")
                                        .build())
                        .build();

        when(gradeRepository.findById("grade-1"))
                .thenReturn(Optional.of(grade));

        when(teacherRepository.findById("teacher-1"))
                .thenReturn(Optional.of(teacher));

        when(gradeHistoryRepository.save(any(JGradeHistory.class)))
                .thenReturn(historyEntity);

        gradeHistoryService.saveGradeHistory(model);

        ArgumentCaptor<JGradeHistory> captor =
                ArgumentCaptor.forClass(JGradeHistory.class);

        verify(gradeHistoryRepository).save(captor.capture());

        JGradeHistory savedEntity = captor.getValue();

        assertEquals(grade, savedEntity.getGrade());
        assertEquals(teacher, savedEntity.getTeacher());
        assertEquals(10.0, savedEntity.getOldScore());
        assertEquals(15.0, savedEntity.getNewScore());

        verify(gradeRepository).findById("grade-1");
        verify(teacherRepository).findById("teacher-1");
    }

    @Test
    void shouldThrowExceptionWhenGradeNotFound() {
        GradeHistory model =
                GradeHistory.builder()
                        .id("history-1")
                        .oldScore(10.0)
                        .newScore(15.0)
                        .reason("Correction de la note")
                        .grade(
                                com.example.demo.model.Grade.builder()
                                        .id("unknown")
                                        .score(10.0)
                                        .build())
                        .build();

        when(gradeRepository.findById("unknown"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () ->
                                gradeHistoryService.saveGradeHistory(model));

        assertEquals("Grade not found", exception.getMessage());

        verify(gradeRepository).findById("unknown");
        verify(gradeHistoryRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenTeacherNotFound() {
        GradeHistory model =
                GradeHistory.builder()
                        .id("history-1")
                        .oldScore(10.0)
                        .newScore(15.0)
                        .reason("Correction de la note")
                        .teacher(
                                com.example.demo.model.Teacher.builder()
                                        .id("unknown")
                                        .build())
                        .build();

        when(teacherRepository.findById("unknown"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () ->
                                gradeHistoryService.saveGradeHistory(model));

        assertEquals("Teacher not found", exception.getMessage());

        verify(teacherRepository).findById("unknown");
        verify(gradeHistoryRepository, never()).save(any());
    }

    @Test
    void shouldRejectInvalidNewScore() {
        GradeHistory model =
                GradeHistory.builder()
                        .id("history-1")
                        .oldScore(10.0)
                        .newScore(25.0)
                        .reason("Correction de la note")
                        .build();

        assertThrows(
                RuntimeException.class,
                () ->
                        gradeHistoryService.saveGradeHistory(model));

        verify(gradeHistoryRepository, never()).save(any());
    }

    @Test
    void shouldRejectHistoryWithoutReason() {
        GradeHistory model =
                GradeHistory.builder()
                        .id("history-1")
                        .oldScore(10.0)
                        .newScore(15.0)
                        .reason("")
                        .build();

        assertThrows(
                RuntimeException.class,
                () ->
                        gradeHistoryService.saveGradeHistory(model));

        verify(gradeHistoryRepository, never()).save(any());
    }
}