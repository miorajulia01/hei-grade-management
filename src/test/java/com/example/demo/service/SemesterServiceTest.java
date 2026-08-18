package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JAcademicYear;
import com.example.demo.entity.JSemester;
import com.example.demo.model.AcademicYear;
import com.example.demo.model.Semester;
import com.example.demo.repository.AcademicYearRepository;
import com.example.demo.repository.SemesterRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SemesterServiceTest {

    @Mock
    private SemesterRepository semesterRepository;

    @Mock
    private AcademicYearRepository academicYearRepository;

    @InjectMocks
    private SemesterService semesterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSemesters() {
        JSemester entity = JSemester.builder().id("1").code("S1").label("Semestre 1").build();
        when(semesterRepository.findAll()).thenReturn(List.of(entity));

        List<Semester> result = semesterService.getAllSemesters();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("S1", result.get(0).getCode());
        verify(semesterRepository, times(1)).findAll();
    }

    @Test
    void testGetSemesterById_Success() {
        JSemester entity = JSemester.builder().id("1").code("S1").build();
        when(semesterRepository.findById("1")).thenReturn(Optional.of(entity));

        Semester result = semesterService.getSemesterById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(semesterRepository, times(1)).findById("1");
    }

    @Test
    void testGetSemesterById_NotFound() {
        when(semesterRepository.findById("99")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> semesterService.getSemesterById("99"));
    }

    @Test
    void testSaveSemester() {
        AcademicYear ayModel = new AcademicYear();
        ayModel.setId("ay-1");

        Semester model = new Semester();
        model.setId("1");
        model.setCode("S1");
        model.setAcademicYear(ayModel);

        JAcademicYear ayEntity = JAcademicYear.builder().id("ay-1").build();
        JSemester semesterEntity = JSemester.builder().id("1").code("S1").academicYear(ayEntity).build();

        when(academicYearRepository.findById("ay-1")).thenReturn(Optional.of(ayEntity));
        when(semesterRepository.save(any(JSemester.class))).thenReturn(semesterEntity);

        Semester saved = semesterService.saveSemester(model);

        assertNotNull(saved);
        assertEquals("S1", saved.getCode());
        verify(semesterRepository, times(1)).save(any(JSemester.class));
    }
}