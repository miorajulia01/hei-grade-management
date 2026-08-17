package com.example.demo.service;

import com.example.demo.entity.JProgram;
import com.example.demo.mapper.ProgramMapper;
import com.example.demo.model.Program;
import com.example.demo.repository.ProgramRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParcoursService {

  private final ProgramRepository programRepository;
  private final ProgramMapper programMapper;

  public ParcoursService(ProgramRepository programRepository, ProgramMapper programMapper) {
    this.programRepository = programRepository;
    this.programMapper = programMapper;
  }

  @Transactional(readOnly = true)
  public List<Program> getParcours(String name) {
    List<JProgram> entities;

    if (name != null && !name.isBlank()) {
      entities = programRepository.findByName(name).map(List::of).orElse(List.of());
    } else {
      entities = programRepository.findAll();
    }

    return entities.stream().map(programMapper::toModel).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Program getParcoursById(String id) {
    return programRepository
        .findById(id)
        .map(programMapper::toModel)
        .orElseThrow(() -> new IllegalArgumentException("Program not found with ID: " + id));
  }
}
