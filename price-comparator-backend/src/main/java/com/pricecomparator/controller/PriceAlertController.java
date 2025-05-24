package com.pricecomparator.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pricecomparator.model.PriceAlert;
import com.pricecomparator.service.PriceAlertService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class PriceAlertController {

    private final PriceAlertService priceAlertService;

    @PostMapping
    public PriceAlert createAlert(@RequestBody PriceAlert alert) {
        if (alert.getId() == null || alert.getId().isEmpty()) {
            alert.setId(UUID.randomUUID().toString());
        }
        return priceAlertService.createAlert(alert);
    }

    @GetMapping("/user/{id}")
    public List<PriceAlert> getAlertsForUser(@PathVariable String id) {
        return priceAlertService.getAlertsByUser(id);
    }
}
