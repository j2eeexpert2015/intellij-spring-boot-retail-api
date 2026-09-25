package org.eclipsefeaturesdemo.retail.data;

import java.math.BigDecimal;

import org.eclipsefeaturesdemo.retail.model.Product;
import org.eclipsefeaturesdemo.retail.model.ProductCategory;
import org.eclipsefeaturesdemo.retail.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
    	// TODO: review validation
        if (productRepository.count() > 0) {
            return;
        }

        productRepository.save(new Product(
                "Wireless Mouse",
                ProductCategory.ELECTRONICS,
                new BigDecimal("25.99"),
                12,
                true));

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

        productRepository.save(new Product(
                "Desk Lamp",
                ProductCategory.HOME,
                new BigDecimal("34.99"),
                3,
                true));

        productRepository.save(new Product(
                "Java Concurrency Guide",
                ProductCategory.BOOKS,
                new BigDecimal("39.99"),
                8,
                true));
        //Adding one inactive product 
        productRepository.save(new Product(
                "Legacy USB Hub",
                ProductCategory.ELECTRONICS,
                new BigDecimal("15.99"),
                6,
                false));
    }
}
