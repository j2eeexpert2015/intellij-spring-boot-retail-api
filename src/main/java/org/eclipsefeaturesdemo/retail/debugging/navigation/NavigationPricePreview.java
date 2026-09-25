package org.eclipsefeaturesdemo.retail.debugging.navigation;

import java.math.BigDecimal;

public record NavigationPricePreview(
        BigDecimal unitPrice,
        int quantity,
        String customerTier,
        BigDecimal subtotal,
        BigDecimal discount,
        BigDecimal discountedSubtotal,
        BigDecimal deliveryFee,
        BigDecimal tax,
        BigDecimal total,
        String message) {
}
