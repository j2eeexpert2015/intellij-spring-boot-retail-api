package org.eclipsefeaturesdemo.retail.controller;

import java.util.List;

import org.eclipsefeaturesdemo.retail.dto.InventoryReportRow;
import org.eclipsefeaturesdemo.retail.dto.ProductRequest;
import org.eclipsefeaturesdemo.retail.dto.ProductResponse;
import org.eclipsefeaturesdemo.retail.model.ProductCategory;
import org.eclipsefeaturesdemo.retail.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> findProducts(@RequestParam(required = false) ProductCategory category) {
        return productService.findProducts(category);
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping("/low-stock")
    public List<ProductResponse> findLowStockProducts() {
        return productService.findLowStockProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return productService.create(request);
    }

    @GetMapping("/inventory-report")
    public List<InventoryReportRow> generateInventoryReport() {
        return productService.generateInventoryReport();
    }
}
