package org.eclipsefeaturesdemo.retail.dto;

import java.math.BigDecimal;

import org.eclipsefeaturesdemo.retail.model.ProductCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequest(
        @NotBlank(message = "Product name must not be blank")
        String name,

        @NotNull(message = "Product category is required")
        ProductCategory category,

        @NotNull(message = "Product price is required")
        @Positive(message = "Product price must be positive")
        BigDecimal price,

        @NotNull(message = "Available quantity is required")
        @PositiveOrZero(message = "Available quantity cannot be negative")
        Integer availableQuantity,

        Boolean active
) {
}
