package com.example.demo.mapper;

import com.example.demo.entity.JParcours;
import com.example.demo.model.Parcours;
import org.springframework.stereotype.Component;

@Component
public class ParcoursMapper {
  public Parcours toModel(JParcours entity) {
    if (entity == null) return null;
    return Parcours.builder().id(entity.getId()).name(entity.getName()).build();
  }
}
