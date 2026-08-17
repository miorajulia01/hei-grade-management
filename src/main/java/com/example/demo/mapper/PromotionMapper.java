package com.example.demo.mapper;

import com.example.demo.entity.JPromotion;
import com.example.demo.model.Promotion;

public class PromotionMapper {
  public static Promotion toModel(JPromotion entity) {
    if (entity == null) return null;
    return Promotion.builder()
            .id(entity.getId())
            .ref(entity.getRef())
            .build();
  }
}