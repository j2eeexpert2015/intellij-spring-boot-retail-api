package org.eclipsefeaturesdemo.retail.debugging.navigation;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debug/navigation")
public class NavigationPricingController {

    private final NavigationPricingService navigationPricingService;

    public NavigationPricingController(
            NavigationPricingService navigationPricingService) {
        this.navigationPricingService = navigationPricingService;
    }

    @GetMapping("/price-preview")
    public NavigationPricePreview preview(
            @RequestParam BigDecimal unitPrice,
            @RequestParam int quantity,
            @RequestParam String customerTier) {

        return navigationPricingService.preview(
                unitPrice,
                quantity,
                customerTier);
    }
}
