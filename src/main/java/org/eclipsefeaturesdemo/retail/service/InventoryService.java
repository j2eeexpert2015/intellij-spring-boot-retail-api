package org.eclipsefeaturesdemo.retail.service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

import org.eclipsefeaturesdemo.retail.dto.StockAdjustmentRequest;
import org.eclipsefeaturesdemo.retail.dto.StockAdjustmentResponse;
import org.eclipsefeaturesdemo.retail.exception.ProductNotFoundException;
import org.eclipsefeaturesdemo.retail.model.Product;
import org.eclipsefeaturesdemo.retail.model.StockAdjustment;
import org.eclipsefeaturesdemo.retail.repository.ProductRepository;
import org.eclipsefeaturesdemo.retail.repository.StockAdjustmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final ProductRepository productRepository;
    private final StockAdjustmentRepository stockAdjustmentRepository;

    public InventoryService(
            ProductRepository productRepository,
            StockAdjustmentRepository stockAdjustmentRepository) {

        this.productRepository = productRepository;
        this.stockAdjustmentRepository = stockAdjustmentRepository;
    }

    @Transactional(readOnly = true)
    public List<StockAdjustmentResponse> findAdjustments(Long productId) {
        List<StockAdjustment> adjustments = productId == null
                ? stockAdjustmentRepository.findAll()
                : stockAdjustmentRepository.findByProductIdOrderByCreatedAtDesc(productId);

        return adjustments.stream()
                .sorted(Comparator.comparing(StockAdjustment::getCreatedAt).reversed())
                .map(adjustment -> toResponse(adjustment, null))
                .toList();
    }

    @Transactional
    public StockAdjustmentResponse adjustStock(StockAdjustmentRequest request) {
    	// TODO: review validation
        if (request.quantity() == 0) {
            throw new IllegalArgumentException("Stock adjustment quantity cannot be zero");
        }

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ProductNotFoundException(request.productId()));

        int updatedQuantity = product.getAvailableQuantity() + request.quantity();
        if (updatedQuantity < 0) {
            throw new IllegalArgumentException("Stock adjustment cannot make available quantity negative");
        }

        product.setAvailableQuantity(updatedQuantity);
        productRepository.save(product);

        StockAdjustment adjustment = new StockAdjustment(
                product.getId(),
                request.quantity(),
                request.reason(),
                Instant.now());
        
        Integer responseQuantity = Integer.valueOf(updatedQuantity);


        StockAdjustment savedAdjustment = stockAdjustmentRepository.save(adjustment);

        return toResponse(savedAdjustment, responseQuantity);
    }

    private StockAdjustmentResponse toResponse(StockAdjustment adjustment, Integer availableQuantityAfterAdjustment) {
        return new StockAdjustmentResponse(
                adjustment.getId(),
                adjustment.getProductId(),
                adjustment.getQuantity(),
                adjustment.getReason(),
                adjustment.getCreatedAt(),
                availableQuantityAfterAdjustment);
    }
}
