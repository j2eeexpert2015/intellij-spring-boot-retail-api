package org.eclipsefeaturesdemo.retail.debugging.hotcode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class DeliveryFeeService {

    private static final BigDecimal FREE_DELIVERY_THRESHOLD = new BigDecimal("100.00");
    private static final BigDecimal STANDARD_DELIVERY_FEE = new BigDecimal("9.99");
    private static final BigDecimal FREE_DELIVERY_FEE = new BigDecimal("0.00");

    private final AtomicInteger quoteSequence = new AtomicInteger();

    public DeliveryFeeQuote calculate(BigDecimal orderTotal) {
        BigDecimal normalizedOrderTotal = orderTotal.setScale(2, RoundingMode.HALF_UP);

        // Business rule: an order of $100.00 or more qualifies for free delivery.
        boolean freeDelivery = normalizedOrderTotal.compareTo(FREE_DELIVERY_THRESHOLD) > 0;

        BigDecimal deliveryFee = freeDelivery
                ? FREE_DELIVERY_FEE
                : STANDARD_DELIVERY_FEE;

        int quoteNumber = quoteSequence.incrementAndGet();

        return new DeliveryFeeQuote(
                quoteNumber,
                normalizedOrderTotal,
                FREE_DELIVERY_THRESHOLD,
                deliveryFee);
    }
}

