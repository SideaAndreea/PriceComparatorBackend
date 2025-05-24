package com.pricecomparator.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pricecomparator.model.PriceAlert;

@Service
public class PriceAlertService {

    private final Map<String, List<PriceAlert>> alertsByUser = new HashMap<>();

    public PriceAlert createAlert(PriceAlert alert) {
        alertsByUser.computeIfAbsent(alert.getUserId(), k -> new ArrayList<>()).add(alert);
        return alert;
    }

    public List<PriceAlert> getAlertsByUser(String userId) {
        return alertsByUser.getOrDefault(userId, List.of());
    }
}

