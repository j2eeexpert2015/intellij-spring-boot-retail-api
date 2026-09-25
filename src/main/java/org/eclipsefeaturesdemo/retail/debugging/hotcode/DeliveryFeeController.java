package org.eclipsefeaturesdemo.retail.debugging.hotcode;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debug/hot-code")
public class DeliveryFeeController {

    private final DeliveryFeeService deliveryFeeService;

    public DeliveryFeeController(DeliveryFeeService deliveryFeeService) {
        this.deliveryFeeService = deliveryFeeService;
    }

    @GetMapping("/delivery-fee")
    public DeliveryFeeQuote calculateDeliveryFee(@RequestParam BigDecimal orderTotal) {
        return deliveryFeeService.calculate(orderTotal);
    }
}

