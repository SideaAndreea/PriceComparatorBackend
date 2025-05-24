package com.pricecomparator.controller;
import com.pricecomparator.model.Product;

import com.pricecomparator.service.CsvLoaderService;
import com.pricecomparator.service.PriceHistoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final CsvLoaderService csvLoaderService;

    @GetMapping("/store/{store}/date/{date}")
    public ResponseEntity<?> getProducts(
            @PathVariable String store,
            @PathVariable String date) {
        String filename = store.toLowerCase() + "_" + date + ".csv";
        try {
            List<Product> products = csvLoaderService.loadProducts(filename);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(500).body("Eroare la citirea fișierului: " + e.getMessage());
        }
    }
    
    private final PriceHistoryService priceHistoryService;
    
    @GetMapping("/price-history")
    public ResponseEntity<?> getPriceHistory(
            @RequestParam String productName,
            @RequestParam String store) {

        List<PriceHistoryService.PriceDataPoint> data = priceHistoryService.getPriceHistory(productName, store);
        return ResponseEntity.ok(data);
    }

}