package org.intellijfeaturesdemo.retail.dto;

import java.math.BigDecimal;

import org.intellijfeaturesdemo.retail.model.ProductCategory;

public record InventoryReportRow(
        Long productId,
        String productName,
        ProductCategory category,
        BigDecimal price,
        Integer availableQuantity,
        BigDecimal inventoryValue,
        boolean lowStock) {
}

