package com.example.demo.mapper;

import com.example.demo.entity.JProgram;
import com.example.demo.model.Program;

public class ProgramMapper {
  public static Program toModel(JProgram entity) {
    if (entity == null) return null;
    return Program.builder()
            .id(entity.getId())
            .code(entity.getCode())
            .label(entity.getLabel())
            .description(entity.getDescription())
            .build();
  }

  public static JProgram toEntity(Program model) {
    if (model == null) return null;
    return JProgram.builder()
            .id(model.getId())
            .code(model.getCode())
            .label(model.getLabel())
            .description(model.getDescription())
            .build();
  }
}