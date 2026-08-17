package com.example.demo.mapper;

import com.example.demo.entity.JProgram;
import com.example.demo.model.Program;
import org.springframework.stereotype.Component;

@Component
public class ParcoursMapper {
  public Program toModel(JProgram entity) {
    if (entity == null) return null;
    return Program.builder().id(entity.getId()).name(entity.getName()).build();
  }
}
