package org.eclipsefeaturesdemo.retail.debugging.callstack;

import org.springframework.stereotype.Service;

@Service
public class ShippingCostService {

    private static final double BASE_CHARGE = 5.0;
    private static final double CHARGE_PER_KILOGRAM = 2.0;
    private static final double CHARGE_PER_KILOMETRE = 0.1;
    
    public double calculate(double weightKg, double distanceKm) {
        double shippingCost = BASE_CHARGE
                + (weightKg * CHARGE_PER_KILOGRAM)
                + (distanceKm * CHARGE_PER_KILOMETRE);

        return shippingCost;
    }

}
