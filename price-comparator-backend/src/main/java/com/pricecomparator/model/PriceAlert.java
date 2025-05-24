package com.pricecomparator.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceAlert {
	private String id;
    private String userId;
    private String productId;
    private double targetPrice;
    private boolean active;
}
