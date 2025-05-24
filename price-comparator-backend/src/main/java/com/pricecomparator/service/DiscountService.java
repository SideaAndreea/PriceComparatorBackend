package com.pricecomparator.service;

import java.io.FileReader;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.pricecomparator.model.Discount;

@Service
public class DiscountService {

    public List<Discount> loadDiscounts(String filename) throws IOException, CsvValidationException, NumberFormatException {
        List<Discount> discounts = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader("src/main/resources/csv/" + filename))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {
            reader.skip(1); // Skip header
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length < 9) {
                    continue;
                }

                discounts.add(new Discount(
                        line[0],
                        line[1],
                        line[2],
                        Double.parseDouble(line[3]),
                        line[4],
                        line[5],
                        LocalDate.parse(line[6]),
                        LocalDate.parse(line[7]),
                        Double.parseDouble(line[8]), filename
                ));
            }
        }
        return discounts;
    }

    public List<Discount> getActiveDiscounts(List<Discount> all, LocalDate date) {
        return all.stream()
                .filter(d -> (d.getFromDate().compareTo(date) <= 0 && d.getToDate().compareTo(date) >= 0))
                .collect(Collectors.toList());
    }

    public List<Discount> getNewDiscounts(List<Discount> all, LocalDate currentDate) {
        return all.stream()
                .filter(d -> d.getFromDate().isAfter(currentDate.minusDays(1)) &&
                             d.getFromDate().isBefore(currentDate.plusDays(1)))
                .collect(Collectors.toList());
    }

    public List<Discount> getTopDiscounts(List<Discount> all, int limit) {
        return all.stream()
                .sorted(Comparator.comparingDouble(Discount::getPercentage).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }
}