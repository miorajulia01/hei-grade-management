package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JProgram;
import com.example.demo.model.Program;
import com.example.demo.repository.ProgramRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProgramServiceTest {

  @Mock private ProgramRepository programRepository;

  @InjectMocks private ProgramService programService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllPrograms() {
    JProgram entity = JProgram.builder().id("1").code("PROG").label("Computer Science").build();
    when(programRepository.findAll()).thenReturn(List.of(entity));

    List<Program> result = programService.getAllPrograms();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("PROG", result.get(0).getCode());
    verify(programRepository, times(1)).findAll();
  }

  @Test
  void testGetProgramById_Success() {
    JProgram entity = JProgram.builder().id("1").code("PROG").build();
    when(programRepository.findById("1")).thenReturn(Optional.of(entity));

    Program result = programService.getProgramById("1");

    assertNotNull(result);
    assertEquals("1", result.getId());
    verify(programRepository, times(1)).findById("1");
  }

  @Test
  void testGetProgramById_NotFound() {
    when(programRepository.findById("99")).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> programService.getProgramById("99"));
  }

  @Test
  void testSaveProgram() {
    Program model = new Program();
    model.setId("1");
    model.setCode("PROG");

    JProgram entity = JProgram.builder().id("1").code("PROG").build();

    when(programRepository.save(any(JProgram.class))).thenReturn(entity);

    Program saved = programService.saveProgram(model);

    assertNotNull(saved);
    assertEquals("PROG", saved.getCode());
    verify(programRepository, times(1)).save(any(JProgram.class));
  }
}
