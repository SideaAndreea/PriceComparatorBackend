package com.pricecomparator.service;

import com.pricecomparator.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceHistoryService {

    private final CsvLoaderService csvLoaderService;

    public List<PriceDataPoint> getPriceHistory(String productName, String store) {
        File folder = new File("src/main/resources/csv/");
        File[] files = folder.listFiles((dir, name) ->
        name.startsWith(store.toLowerCase()) && name.endsWith(".csv") && !name.contains("discounts"));


        if (files == null) return List.of();

        List<PriceDataPoint> history = new ArrayList<>();

        for (File file : files) {
            String filename = file.getName();
            String[] parts = filename.split("_|\\.");
            if (parts.length < 3) continue;

            try {
            	String dateString = parts[1];
            	
                LocalDate date = LocalDate.parse(dateString);

                List<Product> products = csvLoaderService.loadProducts(filename);

                products.stream()
                        .filter(p -> p.getProductName().equalsIgnoreCase(productName))
                        .findFirst()
                        .ifPresent(p -> history.add(new PriceDataPoint(date, p.getPrice())));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return history.stream()
                .sorted(Comparator.comparing(PriceDataPoint::getDate))
                .collect(Collectors.toList());
    }

    public static class PriceDataPoint {
        private LocalDate date;
        private double price;

        public PriceDataPoint(LocalDate date, double price) {
            this.date = date;
            this.price = price;
        }

        public LocalDate getDate() {
            return date;
        }

        public double getPrice() {
            return price;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
