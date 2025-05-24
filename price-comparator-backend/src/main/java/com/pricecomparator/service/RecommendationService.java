package com.pricecomparator.service;

import com.pricecomparator.model.Product;
import com.pricecomparator.model.ProductRecommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final CsvLoaderService csvLoaderService;

    public List<ProductRecommendation> getSubstitutes(String productId) {
        List<Product> allProducts = loadAllProducts();

        Optional<Product> productOpt = allProducts.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst();

        if (productOpt.isEmpty()) {
            return List.of();
        }

        Product product = productOpt.get();

        return allProducts.stream()
                .filter(p -> !p.getProductId().equals(productId)) // alt produs
                .filter(p -> p.getCategory().equalsIgnoreCase(product.getCategory())) // aceeași categorie
                .map(p -> new ProductRecommendation(p.getProductId(), p.getProductName(), p.getPrice()))
                .collect(Collectors.toList());
    }

    private List<Product> loadAllProducts() {
        List<Product> all = new ArrayList<>();
        File folder = new File("src/main/resources/csv/");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv") && !name.contains("discounts"));

        if (files == null) return all;

        for (File file : files) {
            try {
                all.addAll(csvLoaderService.loadProducts(file.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return all;
    }
}
