package org.eclipsefeaturesdemo.retail.dto;

import java.time.Instant;

public record StockAdjustmentResponse(
        Long id,
        Long productId,
        Integer quantity,
        String reason,
        Instant createdAt,
        Integer availableQuantityAfterAdjustment
) {
}
