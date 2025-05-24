package com.pricecomparator.controller;

import com.pricecomparator.model.ProductRecommendation;
import com.pricecomparator.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/{id}/substitutes")
    public List<ProductRecommendation> getSubstitutes(@PathVariable String id) {
        return recommendationService.getSubstitutes(id);
    }
}
