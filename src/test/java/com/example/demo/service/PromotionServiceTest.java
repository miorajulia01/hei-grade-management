package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JAcademicYear;
import com.example.demo.entity.JPromotion;
import com.example.demo.model.AcademicYear;
import com.example.demo.model.Promotion;
import com.example.demo.repository.AcademicYearRepository;
import com.example.demo.repository.PromotionRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PromotionServiceTest {

  @Mock private PromotionRepository promotionRepository;

  @Mock private AcademicYearRepository academicYearRepository;

  @InjectMocks private PromotionService promotionService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllPromotions() {
    JPromotion entity = JPromotion.builder().id("1").ref("PROMO1").label("Licence 1").build();
    when(promotionRepository.findAll()).thenReturn(List.of(entity));

    List<Promotion> result = promotionService.getAllPromotions();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("PROMO1", result.get(0).getRef());
    verify(promotionRepository, times(1)).findAll();
  }

  @Test
  void testGetPromotionById_Success() {
    JPromotion entity = JPromotion.builder().id("1").ref("PROMO1").build();
    when(promotionRepository.findById("1")).thenReturn(Optional.of(entity));

    Promotion result = promotionService.getPromotionById("1");

    assertNotNull(result);
    assertEquals("1", result.getId());
    verify(promotionRepository, times(1)).findById("1");
  }

  @Test
  void testGetPromotionById_NotFound() {
    when(promotionRepository.findById("99")).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> promotionService.getPromotionById("99"));
  }

  @Test
  void testSavePromotion_WithAcademicYear() {
    AcademicYear ayModel = new AcademicYear();
    ayModel.setId("ay-1");

    Promotion model = new Promotion();
    model.setId("1");
    model.setRef("P1");
    model.setLabel("Promo 1");
    model.setAcademicYear(ayModel);

    JAcademicYear ayEntity = JAcademicYear.builder().id("ay-1").label("2023").build();
    JPromotion promoEntity = JPromotion.builder().id("1").ref("P1").academicYear(ayEntity).build();

    when(academicYearRepository.findById("ay-1")).thenReturn(Optional.of(ayEntity));
    when(promotionRepository.save(any(JPromotion.class))).thenReturn(promoEntity);

    Promotion saved = promotionService.savePromotion(model);

    assertNotNull(saved);
    assertEquals("P1", saved.getRef());
    verify(academicYearRepository, times(1)).findById("ay-1");
    verify(promotionRepository, times(1)).save(any(JPromotion.class));
  }
}
