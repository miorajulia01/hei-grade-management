package com.example.demo.controller;

import com.example.demo.model.Promotion;
import com.example.demo.service.PromotionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotions")
@RequiredArgsConstructor
public class PromotionController {

  private final PromotionService promotionService;

  @GetMapping
  public List<Promotion> getAll() {
    return promotionService.getAllPromotions();
  }

  @GetMapping("/{id}")
  public Promotion getById(@PathVariable String id) {
    return promotionService.getPromotionById(id);
  }

  @PostMapping
  public Promotion create(@RequestBody Promotion promotion) {
    return promotionService.savePromotion(promotion);
  }

  @PutMapping("/{id}")
  public Promotion update(@PathVariable String id, @RequestBody Promotion promotion) {
    return promotionService.updatePromotion(id, promotion);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    promotionService.deletePromotion(id);
    return ResponseEntity.noContent().build();
  }
}
