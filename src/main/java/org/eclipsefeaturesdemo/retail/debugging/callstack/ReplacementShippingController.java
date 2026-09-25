package org.eclipsefeaturesdemo.retail.debugging.callstack;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debug/call-stack/replacement")
public class ReplacementShippingController {

    private final ShippingCostService shippingCostService;

    public ReplacementShippingController(ShippingCostService shippingCostService) {
        this.shippingCostService = shippingCostService;
    }

    @GetMapping("/shipping")
    public double calculateShipping(
            @RequestParam("weightKg") double weightKg,
            @RequestParam("distanceKm") double distanceKm) {

        return shippingCostService.calculate(weightKg, distanceKm);
    }
}
