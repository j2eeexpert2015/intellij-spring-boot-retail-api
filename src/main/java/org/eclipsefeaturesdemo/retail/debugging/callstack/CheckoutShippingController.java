package org.eclipsefeaturesdemo.retail.debugging.callstack;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debug/call-stack/checkout")
public class CheckoutShippingController {

    private final ShippingCostService shippingCostService;

    public CheckoutShippingController(ShippingCostService shippingCostService) {
        this.shippingCostService = shippingCostService;
    }

    @GetMapping("/shipping")
    public double calculateShipping(
            @RequestParam("weightKg") double weightKg,
            @RequestParam("distanceKm") double distanceKm) {

        // Deliberate caller-level defect for the call-stack debugging lesson.
        return shippingCostService.calculate(distanceKm, weightKg);
    }
}
