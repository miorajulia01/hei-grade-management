package com.example.demo.service;

import com.example.demo.entity.JPromotion;
import com.example.demo.mapper.PromotionMapper;
import com.example.demo.model.Promotion;
import com.example.demo.repository.PromotionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PromotionService {

  private final PromotionRepository promotionRepository;
  private final PromotionMapper promotionMapper;

  public PromotionService(
      PromotionRepository promotionRepository, PromotionMapper promotionMapper) {
    this.promotionRepository = promotionRepository;
    this.promotionMapper = promotionMapper;
  }

  @Transactional(readOnly = true)
  public List<Promotion> getPromotions(String name) {
    List<JPromotion> entities;

    if (name != null && !name.isBlank()) {
      entities = promotionRepository.findByNameContainingIgnoreCase(name);
    } else {
      entities = promotionRepository.findAll();
    }

    return entities.stream().map(promotionMapper::toModel).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Promotion getPromotionById(String id) {
    return promotionRepository
        .findById(id)
        .map(promotionMapper::toModel)
        .orElseThrow(() -> new IllegalArgumentException("Promotion not found with ID: " + id));
  }
}
