package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JAcademicYear;
import com.example.demo.model.AcademicYear;
import com.example.demo.repository.AcademicYearRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AcademicYearServiceTest {

  @Mock private AcademicYearRepository academicYearRepository;

  @InjectMocks private AcademicYearService academicYearService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllAcademicYears() {
    JAcademicYear entity = JAcademicYear.builder().id("1").label("2023-2024").build();
    when(academicYearRepository.findAll()).thenReturn(List.of(entity));

    List<AcademicYear> result = academicYearService.getAllAcademicYears();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("2023-2024", result.get(0).getLabel());
    verify(academicYearRepository, times(1)).findAll();
  }

  @Test
  void testGetAcademicYearById_Success() {
    JAcademicYear entity = JAcademicYear.builder().id("1").label("2023-2024").build();
    when(academicYearRepository.findById("1")).thenReturn(Optional.of(entity));

    AcademicYear result = academicYearService.getAcademicYearById("1");

    assertNotNull(result);
    assertEquals("1", result.getId());
    verify(academicYearRepository, times(1)).findById("1");
  }

  @Test
  void testGetAcademicYearById_NotFound() {
    when(academicYearRepository.findById("99")).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> academicYearService.getAcademicYearById("99"));
  }

  @Test
  void testSaveAcademicYear() {
    AcademicYear model =
        new AcademicYear("1", "2023-2024", LocalDate.now(), LocalDate.now().plusYears(1));
    JAcademicYear entity = JAcademicYear.builder().id("1").label("2023-2024").build();

    when(academicYearRepository.save(any(JAcademicYear.class))).thenReturn(entity);

    AcademicYear saved = academicYearService.saveAcademicYear(model);

    assertNotNull(saved);
    assertEquals("2023-2024", saved.getLabel());
    verify(academicYearRepository, times(1)).save(any(JAcademicYear.class));
  }
}
