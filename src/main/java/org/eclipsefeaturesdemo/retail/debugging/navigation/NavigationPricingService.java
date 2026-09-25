package org.eclipsefeaturesdemo.retail.debugging.navigation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class NavigationPricingService {

    private static final BigDecimal GOLD_DISCOUNT_RATE = new BigDecimal("0.10");
    private static final BigDecimal SILVER_DISCOUNT_RATE = new BigDecimal("0.05");
    private static final BigDecimal TAX_RATE = new BigDecimal("0.08");
    private static final BigDecimal FREE_DELIVERY_THRESHOLD = new BigDecimal("200.00");
    private static final BigDecimal STANDARD_DELIVERY_FEE = new BigDecimal("9.99");
    private static final BigDecimal ZERO_AMOUNT = new BigDecimal("0.00");

    public NavigationPricePreview preview(
            BigDecimal unitPrice,
            int quantity,
            String customerTier) {

        BigDecimal normalizedUnitPrice = requireUnitPrice(unitPrice);
        int normalizedQuantity = requireQuantity(quantity);
        String normalizedTier = normalizeTier(customerTier);

        BigDecimal subtotal = calculateSubtotal(
                normalizedUnitPrice,
                normalizedQuantity);
        BigDecimal discount = calculateDiscount(subtotal, normalizedTier);
        BigDecimal discountedSubtotal = subtotal.subtract(discount);

        PricingComponents components = calculateComponents(
                discountedSubtotal,
                calculateDeliveryFee(discountedSubtotal),
                calculateTax(discountedSubtotal));

        String message = buildMessage(components.deliveryFee());

        return new NavigationPricePreview(
                normalizedUnitPrice,
                normalizedQuantity,
                normalizedTier,
                subtotal,
                discount,
                discountedSubtotal,
                components.deliveryFee(),
                components.tax(),
                components.total(),
                message);
    }

    private BigDecimal requireUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null || unitPrice.signum() <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than zero");
        }
        return unitPrice.setScale(2, RoundingMode.HALF_UP);
    }

    private int requireQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }
        return quantity;
    }

    private String normalizeTier(String customerTier) {
        if (customerTier == null || customerTier.isBlank()) {
            return "STANDARD";
        }
        return customerTier.trim().toUpperCase(Locale.ROOT);
    }

    private BigDecimal calculateSubtotal(
            BigDecimal unitPrice,
            int quantity) {
        return unitPrice.multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDiscount(
            BigDecimal subtotal,
            String customerTier) {

        BigDecimal rate = switch (customerTier) {
            case "GOLD" -> GOLD_DISCOUNT_RATE;
            case "SILVER" -> SILVER_DISCOUNT_RATE;
            default -> BigDecimal.ZERO;
        };

        return subtotal.multiply(rate)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDeliveryFee(BigDecimal discountedSubtotal) {
        return discountedSubtotal.compareTo(FREE_DELIVERY_THRESHOLD) >= 0
                ? ZERO_AMOUNT
                : STANDARD_DELIVERY_FEE;
    }

    private BigDecimal calculateTax(BigDecimal discountedSubtotal) {
        return discountedSubtotal.multiply(TAX_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private PricingComponents calculateComponents(
            BigDecimal discountedSubtotal,
            BigDecimal deliveryFee,
            BigDecimal tax) {

        BigDecimal total = discountedSubtotal
                .add(deliveryFee)
                .add(tax)
                .setScale(2, RoundingMode.HALF_UP);

        return new PricingComponents(deliveryFee, tax, total);
    }

    private String buildMessage(BigDecimal deliveryFee) {
        return deliveryFee.signum() == 0
                ? "Free delivery applied"
                : "Standard delivery applied";
    }

    private record PricingComponents(
            BigDecimal deliveryFee,
            BigDecimal tax,
            BigDecimal total) {
    }
}
