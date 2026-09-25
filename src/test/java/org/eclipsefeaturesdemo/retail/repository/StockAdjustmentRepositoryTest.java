package org.eclipsefeaturesdemo.retail.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;

import org.eclipsefeaturesdemo.retail.model.StockAdjustment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StockAdjustmentRepositoryTest {

    @Autowired
    private StockAdjustmentRepository stockAdjustmentRepository;

    @Test
    void shouldFindAdjustmentsByProductIdNewestFirst() {
        stockAdjustmentRepository.save(new StockAdjustment(
                1L,
                5,
                "New shipment",
                Instant.parse("2026-07-08T10:00:00Z")));

        stockAdjustmentRepository.save(new StockAdjustment(
                1L,
                -2,
                "Damaged stock",
                Instant.parse("2026-07-08T11:00:00Z")));

        stockAdjustmentRepository.save(new StockAdjustment(
                2L,
                3,
                "Inventory correction",
                Instant.parse("2026-07-08T12:00:00Z")));

        List<StockAdjustment> adjustments = stockAdjustmentRepository.findByProductIdOrderByCreatedAtDesc(1L);

        assertThat(adjustments)
                .hasSize(2)
                .extracting(StockAdjustment::getReason)
                .containsExactly("Damaged stock", "New shipment");
    }
}
