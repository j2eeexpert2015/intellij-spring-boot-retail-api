package org.eclipsefeaturesdemo.retail.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.eclipsefeaturesdemo.retail.dto.StockAdjustmentRequest;
import org.eclipsefeaturesdemo.retail.dto.StockAdjustmentResponse;
import org.eclipsefeaturesdemo.retail.model.Product;
import org.eclipsefeaturesdemo.retail.model.ProductCategory;
import org.eclipsefeaturesdemo.retail.model.StockAdjustment;
import org.eclipsefeaturesdemo.retail.repository.ProductRepository;
import org.eclipsefeaturesdemo.retail.repository.StockAdjustmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockAdjustmentRepository stockAdjustmentRepository;

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService(productRepository, stockAdjustmentRepository);
    }

    @Test
    void shouldIncreaseStockAndRecordAdjustment() {
        Product product = new Product(
                "Wireless Mouse",
                ProductCategory.ELECTRONICS,
                new BigDecimal("25.99"),
                12,
                true);
        ReflectionTestUtils.setField(product, "id", 1L);

        StockAdjustment savedAdjustment = new StockAdjustment(1L, 5, "New shipment", java.time.Instant.now());
        ReflectionTestUtils.setField(savedAdjustment, "id", 100L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(stockAdjustmentRepository.save(any(StockAdjustment.class))).thenReturn(savedAdjustment);

        StockAdjustmentResponse response = inventoryService.adjustStock(
                new StockAdjustmentRequest(1L, 5, "New shipment"));

        assertThat(response.productId()).isEqualTo(1L);
        assertThat(response.quantity()).isEqualTo(5);
        assertThat(response.availableQuantityAfterAdjustment()).isEqualTo(17);
        verify(productRepository).save(product);
    }

    @Test
    void shouldRejectZeroQuantityAdjustment() {
        StockAdjustmentRequest request = new StockAdjustmentRequest(1L, 0, "No change");

        assertThatThrownBy(() -> inventoryService.adjustStock(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be zero");
    }

    @Test
    void shouldRejectAdjustmentThatMakesStockNegative() {
        Product product = new Product(
                "Desk Lamp",
                ProductCategory.HOME,
                new BigDecimal("34.99"),
                3,
                true);
        ReflectionTestUtils.setField(product, "id", 2L);

        when(productRepository.findById(2L)).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> inventoryService.adjustStock(
                new StockAdjustmentRequest(2L, -4, "Damaged stock")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("negative");
    }
}
