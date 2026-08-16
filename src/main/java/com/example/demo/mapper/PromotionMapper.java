package com.example.demo.mapper;

import com.example.demo.entity.JPromotion;
import com.example.demo.model.Promotion;
import org.springframework.stereotype.Component;

@Component
public class PromotionMapper {
    public Promotion toModel(JPromotion entity) {
        if (entity == null) return null;
        return Promotion.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}