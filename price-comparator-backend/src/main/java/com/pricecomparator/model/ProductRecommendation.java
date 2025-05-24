package com.pricecomparator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRecommendation {
    private String productId;
    private String productName;
    private double price;
}

