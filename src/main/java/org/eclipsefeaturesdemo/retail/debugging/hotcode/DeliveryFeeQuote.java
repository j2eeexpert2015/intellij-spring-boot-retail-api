package org.eclipsefeaturesdemo.retail.debugging.hotcode;

import java.math.BigDecimal;

public record DeliveryFeeQuote(
        int quoteNumber,
        BigDecimal orderTotal,
        BigDecimal freeDeliveryThreshold,
        BigDecimal deliveryFee) {
}

