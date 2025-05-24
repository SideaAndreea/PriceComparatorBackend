package com.pricecomparator.controller;

import com.pricecomparator.model.BasketItem;
import com.pricecomparator.model.Product;
import com.pricecomparator.service.BasketService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/optimize")
    public ResponseEntity<?> optimizeBasket(
            @RequestBody BasketOptimizationRequest request) {

        Map<String, List<Product>> result = basketService.optimizeBasket(
                request.getItems(), request.getStores(), request.getDate());

        return ResponseEntity.ok(result);
    }

    @Data
    public static class BasketOptimizationRequest {
        private List<BasketItem> items;
        private List<String> stores;
        private String date;
    }
}
