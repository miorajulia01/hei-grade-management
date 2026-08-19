package com.example.demo.controller;

import com.example.demo.service.DiplomaExportService;
import com.example.demo.service.DiplomaService;
import com.example.demo.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/diplomas")
@RequiredArgsConstructor
public class DiplomaViewController {

  private final PromotionService promotionService;
  private final DiplomaService diplomaService;
  private final DiplomaExportService diplomaExportService;

  @GetMapping("/promotions")
  public String promotions(Model model) {
    model.addAttribute("promotions", promotionService.getAllPromotions());
    return "diplomas/promotions";
  }

  @GetMapping("/promotions/{promotionId}")
  public String graduates(@PathVariable String promotionId, Model model) {
    model.addAttribute("promotion", promotionService.getPromotionById(promotionId));
    model.addAttribute("graduates", diplomaService.getGraduatesByPromotion(promotionId));
    return "diplomas/graduates";
  }

  @GetMapping("/promotions/{promotionId}/export")
  public ResponseEntity<byte[]> exportGraduates(@PathVariable String promotionId) {
    byte[] content = diplomaExportService.exportGraduatesByPromotion(promotionId);
    String fileName = "diplomes-" + promotionId + ".xlsx";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(
        MediaType.parseMediaType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());

    return ResponseEntity.ok().headers(headers).body(content);
  }
}
