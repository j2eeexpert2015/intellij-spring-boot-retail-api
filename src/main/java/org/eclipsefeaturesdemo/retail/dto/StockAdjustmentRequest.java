package org.eclipsefeaturesdemo.retail.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StockAdjustmentRequest(
        @NotNull(message = "Product id is required")
        Long productId,

        @NotNull(message = "Stock adjustment quantity is required")
        Integer quantity,

        @NotBlank(message = "Stock adjustment reason must not be blank")
        String reason
) {
}
