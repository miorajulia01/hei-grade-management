package com.example.demo.controller;

import com.example.demo.model.Program;
import com.example.demo.service.ProgramService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/programs")
@RequiredArgsConstructor
public class ProgramController {

  private final ProgramService programService;

  @GetMapping
  public List<Program> getAll() {
    return programService.getAllPrograms();
  }

  @GetMapping("/{id}")
  public Program getById(@PathVariable String id) {
    return programService.getProgramById(id);
  }

  @PostMapping
  public Program create(@RequestBody Program program) {
    return programService.saveProgram(program);
  }

  @PutMapping("/{id}")
  public Program update(@PathVariable String id, @RequestBody Program program) {
    return programService.updateProgram(id, program);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    programService.deleteProgram(id);
    return ResponseEntity.noContent().build();
  }
}
