package org.intellijfeaturesdemo.retail.repository;

import java.util.List;

import org.intellijfeaturesdemo.retail.model.StockAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockAdjustmentRepository extends JpaRepository<StockAdjustment, Long> {

    List<StockAdjustment> findByProductIdOrderByCreatedAtDesc(Long productId);
}
