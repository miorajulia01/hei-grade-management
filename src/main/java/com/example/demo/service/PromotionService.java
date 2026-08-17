package com.example.demo.service;

import com.example.demo.entity.JAcademicYear;
import com.example.demo.entity.JPromotion;
import com.example.demo.mapper.PromotionMapper;
import com.example.demo.model.Promotion;
import com.example.demo.repository.AcademicYearRepository;
import com.example.demo.repository.PromotionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionService {

  private final PromotionRepository promotionRepository;
  private final AcademicYearRepository academicYearRepository;

  public List<Promotion> getAllPromotions() {
    return promotionRepository.findAll().stream()
            .map(PromotionMapper::toModel)
            .toList();
  }

  public Promotion getPromotionById(String id) {
    JPromotion entity = promotionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Promotion not found with id: " + id));
    return PromotionMapper.toModel(entity);
  }

  public Promotion savePromotion(Promotion model) {
    JAcademicYear academicYear = null;
    if (model.getAcademicYear() != null && model.getAcademicYear().getId() != null) {
      academicYear = academicYearRepository.findById(model.getAcademicYear().getId())
              .orElseThrow(() -> new RuntimeException("Academic year not found"));
    }

    JPromotion entity = JPromotion.builder()
            .id(model.getId())
            .academicYear(academicYear)
            .ref(model.getRef())
            .label(model.getLabel())
            .build();

    JPromotion saved = promotionRepository.save(entity);
    return PromotionMapper.toModel(saved);
  }
}