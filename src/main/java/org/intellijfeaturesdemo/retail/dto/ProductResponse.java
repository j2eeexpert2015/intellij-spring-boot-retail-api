package org.intellijfeaturesdemo.retail.dto;

import java.math.BigDecimal;

import org.intellijfeaturesdemo.retail.model.ProductCategory;

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
