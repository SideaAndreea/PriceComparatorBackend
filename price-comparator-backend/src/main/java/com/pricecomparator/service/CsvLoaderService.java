package com.pricecomparator.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pricecomparator.model.Product;

@Service
public class CsvLoaderService {
    public List<Product> loadProducts(String filename) throws Exception {
        List<Product> products = new ArrayList<>();

        ClassPathResource resource = new ClassPathResource("csv/" + filename);
        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(resource.getInputStream()))
                .withSkipLines(1)
                .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] line;
            while ((line = reader.readNext()) != null) {
                System.out.println(Arrays.toString(line));
                Product p = new Product(
                    line[0], line[1], line[2], line[3],
                    Double.parseDouble(line[4]),
                    line[5],
                    Double.parseDouble(line[6]),
                    line[7]
                );
                products.add(p);
            }
        }
        return products;
    }
}
