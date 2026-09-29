package org.intellijfeaturesdemo.retail.repository;

import java.util.List;

import org.intellijfeaturesdemo.retail.model.Product;
import org.intellijfeaturesdemo.retail.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrueOrderByNameAsc();

    List<Product> findByCategoryAndActiveTrueOrderByNameAsc(ProductCategory category);

    List<Product> findByAvailableQuantityLessThanEqualAndActiveTrueOrderByAvailableQuantityAsc(int threshold);
}
