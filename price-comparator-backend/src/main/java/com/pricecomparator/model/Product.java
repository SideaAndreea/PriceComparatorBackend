package com.pricecomparator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String productId;
    private String productName;
    private String category;
    private String brand;
    private double quantity;
    private String unit;
    private double price;
    private String currency;

    @Override
    public String toString() {
        return productName + " - " + price + " " + currency;
    }
}
