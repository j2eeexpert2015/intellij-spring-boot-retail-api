package org.eclipsefeaturesdemo.retail.dto;

import java.math.BigDecimal;

import org.eclipsefeaturesdemo.retail.model.ProductCategory;

public record ProductResponse(
        Long id,
        String name,
        ProductCategory category,
        BigDecimal price,
        String currency,
        Integer availableQuantity,
        boolean active
) {
}
