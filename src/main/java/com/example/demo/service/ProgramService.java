package com.example.demo.service;

import com.example.demo.entity.JProgram;
import com.example.demo.mapper.ProgramMapper;
import com.example.demo.model.Program;
import com.example.demo.repository.ProgramRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProgramService {

  private final ProgramRepository programRepository;

  public List<Program> getAllPrograms() {
    return programRepository.findAll().stream().map(ProgramMapper::toModel).toList();
  }

  public Program getProgramById(String id) {
    JProgram entity =
        programRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Program not found with id: " + id));
    return ProgramMapper.toModel(entity);
  }

  public Program saveProgram(Program model) {
    JProgram entity =
        JProgram.builder()
            .id(model.getId())
            .code(model.getCode())
            .label(model.getLabel())
            .description(model.getDescription())
            .build();
    JProgram saved = programRepository.save(entity);
    return ProgramMapper.toModel(saved);
  }

  public Program updateProgram(String id, Program model) {
    JProgram existing =
        programRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Program not found with id: " + id));

    existing.setCode(model.getCode());
    existing.setLabel(model.getLabel());
    existing.setDescription(model.getDescription());

    JProgram saved = programRepository.save(existing);
    return ProgramMapper.toModel(saved);
  }

  public void deleteProgram(String id) {
    if (!programRepository.existsById(id)) {
      throw new RuntimeException("Program not found with id: " + id);
    }
    programRepository.deleteById(id);
  }
}
