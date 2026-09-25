package org.eclipsefeaturesdemo.retail.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.eclipsefeaturesdemo.retail.model.Product;
import org.eclipsefeaturesdemo.retail.model.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldFindActiveProductsByCategory() {
        productRepository.save(new Product(
                "Wireless Mouse",
                ProductCategory.ELECTRONICS,
                new BigDecimal("25.99"),
                12,
                true));

        productRepository.save(new Product(
                "Inactive Keyboard",
                ProductCategory.ELECTRONICS,
                new BigDecimal("49.99"),
                2,
                false));

        List<Product> products = productRepository
                .findByCategoryAndActiveTrueOrderByNameAsc(ProductCategory.ELECTRONICS);

        assertThat(products)
                .hasSize(1)
                .extracting(Product::getName)
                .containsExactly("Wireless Mouse");
    }

    @Test
    void shouldFindLowStockActiveProducts() {
        productRepository.save(new Product(
                "Organic Coffee Beans",
                ProductCategory.GROCERY,
                new BigDecimal("14.50"),
                4,
                true));

        productRepository.save(new Product(
                "Cotton T-Shirt",
                ProductCategory.FASHION,
                new BigDecimal("19.99"),
                25,
                true));

        List<Product> products = productRepository
                .findByAvailableQuantityLessThanEqualAndActiveTrueOrderByAvailableQuantityAsc(5);

        assertThat(products)
                .hasSize(1)
                .extracting(Product::getName)
                .containsExactly("Organic Coffee Beans");
    }
}
