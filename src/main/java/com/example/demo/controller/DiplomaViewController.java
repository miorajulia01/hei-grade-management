package com.example.demo.controller;

import com.example.demo.service.DiplomaService;
import com.example.demo.service.PromotionService;
import lombok.RequiredArgsConstructor;
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
}
