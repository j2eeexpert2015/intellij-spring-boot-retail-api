package org.eclipsefeaturesdemo.retail.controller;

import java.util.List;

import org.eclipsefeaturesdemo.retail.dto.StockAdjustmentRequest;
import org.eclipsefeaturesdemo.retail.dto.StockAdjustmentResponse;
import org.eclipsefeaturesdemo.retail.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/adjustments")
    @ResponseStatus(HttpStatus.CREATED)
    public StockAdjustmentResponse adjustStock(@Valid @RequestBody StockAdjustmentRequest request) {
        return inventoryService.adjustStock(request);
    }
    
    @PostMapping("/adjustments/caught")
    public ResponseEntity<String> adjustStockCaught(
            @Valid @RequestBody StockAdjustmentRequest request) {

        try {
            inventoryService.adjustStock(request);
            return ResponseEntity.ok("Adjustment completed");
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest()
                    .body("Caught: " + exception.getMessage());
        }
    }
    
    @PostMapping("/adjustments/uncaught")
    public ResponseEntity<String> uncaughtExceptionDemo() {

        Thread thread = new Thread(() -> {
            throw new IllegalArgumentException("Uncaught exception demo");
        }, "exception-breakpoint-demo");

        thread.start();

        return ResponseEntity.ok("Uncaught exception started");
    }

    @GetMapping("/adjustments")
    public List<StockAdjustmentResponse> findAdjustments(@RequestParam(required = false) Long productId) {
        return inventoryService.findAdjustments(productId);
    }
}
