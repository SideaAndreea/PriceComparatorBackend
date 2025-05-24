package com.pricecomparator.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.exceptions.CsvValidationException;
import com.pricecomparator.model.Discount;
import com.pricecomparator.service.DiscountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping("/store/{store}/date/{date}")
    public ResponseEntity<List<Discount>> getActiveDiscounts(
            @PathVariable String store,
            @PathVariable String date) throws IOException, CsvValidationException, NumberFormatException {
        String filename = store.toLowerCase() + "_discounts_" + date + ".csv";
        List<Discount> all = discountService.loadDiscounts(filename);
        List<Discount> active = discountService.getActiveDiscounts(all, LocalDate.parse(date));
        return ResponseEntity.ok(active);
    }

    @GetMapping("/store/{store}/new/{date}")
    public ResponseEntity<List<Discount>> getNewDiscounts(
            @PathVariable String store,
            @PathVariable String date) throws IOException, CsvValidationException, NumberFormatException {
        String filename = store.toLowerCase() + "_discounts_" + date + ".csv";
        List<Discount> all = discountService.loadDiscounts(filename);
        List<Discount> newDiscounts = discountService.getNewDiscounts(all, LocalDate.parse(date));
        return ResponseEntity.ok(newDiscounts);
    }

    @GetMapping("/store/{store}/top/{date}")
    public ResponseEntity<List<Discount>> getTopDiscounts(
            @PathVariable String store,
            @PathVariable String date) throws IOException, CsvValidationException, NumberFormatException {
        String filename = store.toLowerCase() + "_discounts_" + date + ".csv";
        List<Discount> all = discountService.loadDiscounts(filename);
        List<Discount> active = discountService.getActiveDiscounts(all, LocalDate.parse(date));
        List<Discount> top = discountService.getTopDiscounts(active, 5); // Top 5 by default
        return ResponseEntity.ok(top);
    }
}
