package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.model.Diploma;
import com.example.demo.model.Promotion;
import com.example.demo.service.DiplomaExportService;
import com.example.demo.service.DiplomaService;
import com.example.demo.service.PromotionService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
class DiplomaViewControllerTest {

  @Mock private PromotionService promotionService;
  @Mock private DiplomaService diplomaService;
  @Mock private DiplomaExportService diplomaExportService;
  @Mock private Model model;

  @InjectMocks private DiplomaViewController diplomaViewController;

  @Test
  void shouldRenderPromotionsPage() {
    List<Promotion> promotions =
        List.of(Promotion.builder().id("promotion-1").label("Promotion H").build());
    when(promotionService.getAllPromotions()).thenReturn(promotions);

    String view = diplomaViewController.promotions(model);

    assertEquals("diplomas/promotions", view);
    verify(model).addAttribute("promotions", promotions);
  }

  @Test
  void shouldRenderGraduatesPage() {
    Promotion promotion = Promotion.builder().id("promotion-1").label("Promotion H").build();
    List<Diploma> graduates = List.of(Diploma.builder().studentNumber("STD001").build());
    when(promotionService.getPromotionById("promotion-1")).thenReturn(promotion);
    when(diplomaService.getGraduatesByPromotion("promotion-1")).thenReturn(graduates);

    String view = diplomaViewController.graduates("promotion-1", model);

    assertEquals("diplomas/graduates", view);
    verify(model).addAttribute("promotion", promotion);
    verify(model).addAttribute("graduates", graduates);
  }

  @Test
  void shouldReturnXlsxDownloadResponse() {
    byte[] content = new byte[] {1, 2, 3};
    when(diplomaExportService.exportGraduatesByPromotion("promotion-1")).thenReturn(content);

    ResponseEntity<byte[]> response = diplomaViewController.exportGraduates("promotion-1");

    assertEquals(200, response.getStatusCode().value());
    assertSame(content, response.getBody());
    assertEquals(
        MediaType.parseMediaType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
        response.getHeaders().getContentType());
    String contentDisposition = response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION);
    org.junit.jupiter.api.Assertions.assertNotNull(contentDisposition);
    org.junit.jupiter.api.Assertions.assertTrue(contentDisposition.startsWith("attachment"));
    org.junit.jupiter.api.Assertions.assertTrue(
        contentDisposition.contains("diplomes-promotion-1.xlsx"));
    verify(diplomaExportService).exportGraduatesByPromotion("promotion-1");
  }
}
