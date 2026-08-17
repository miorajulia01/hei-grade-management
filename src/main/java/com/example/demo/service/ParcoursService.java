package com.example.demo.service;

import com.example.demo.entity.JParcours;
import com.example.demo.mapper.ParcoursMapper;
import com.example.demo.model.Parcours;
import com.example.demo.repository.ParcoursRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParcoursService {

  private final ParcoursRepository parcoursRepository;
  private final ParcoursMapper parcoursMapper;

  public ParcoursService(ParcoursRepository parcoursRepository, ParcoursMapper parcoursMapper) {
    this.parcoursRepository = parcoursRepository;
    this.parcoursMapper = parcoursMapper;
  }

  @Transactional(readOnly = true)
  public List<Parcours> getParcours(String name) {
    List<JParcours> entities;

    if (name != null && !name.isBlank()) {
      entities = parcoursRepository.findByName(name).map(List::of).orElse(List.of());
    } else {
      entities = parcoursRepository.findAll();
    }

    return entities.stream().map(parcoursMapper::toModel).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Parcours getParcoursById(String id) {
    return parcoursRepository
        .findById(id)
        .map(parcoursMapper::toModel)
        .orElseThrow(() -> new IllegalArgumentException("Parcours not found with ID: " + id));
  }
}
