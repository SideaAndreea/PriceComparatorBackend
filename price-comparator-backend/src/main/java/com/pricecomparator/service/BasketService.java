package com.pricecomparator.service;

import com.pricecomparator.model.BasketItem;
import com.pricecomparator.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final CsvLoaderService csvLoaderService;

    public Map<String, List<Product>> optimizeBasket(List<BasketItem> basket, List<String> stores, String date) {
        Map<String, List<Product>> result = new HashMap<>();

        for (String store : stores) {
            String filename = store.toLowerCase() + "_" + date + ".csv";
            try {
                List<Product> products = csvLoaderService.loadProducts(filename);
                List<Product> matched = new ArrayList<>();

                for (BasketItem item : basket) {
                    products.stream()
                        .filter(p -> p.getProductName().toLowerCase().contains(item.getProductName().toLowerCase()))
                        .min(Comparator.comparingDouble(Product::getPrice))
                        .ifPresent(matched::add);
                }
                result.put(store, matched);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
