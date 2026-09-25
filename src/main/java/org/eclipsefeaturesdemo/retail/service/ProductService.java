package org.eclipsefeaturesdemo.retail.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.eclipsefeaturesdemo.retail.config.RetailProperties;
import org.eclipsefeaturesdemo.retail.dto.InventoryReportRow;
import org.eclipsefeaturesdemo.retail.dto.ProductRequest;
import org.eclipsefeaturesdemo.retail.dto.ProductResponse;
import org.eclipsefeaturesdemo.retail.exception.ProductNotFoundException;
import org.eclipsefeaturesdemo.retail.model.Product;
import org.eclipsefeaturesdemo.retail.model.ProductCategory;
import org.eclipsefeaturesdemo.retail.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final RetailProperties retailProperties;

    public ProductService(ProductRepository productRepository, RetailProperties retailProperties) {
        this.productRepository = productRepository;
        this.retailProperties = retailProperties;
    }

    public List<ProductResponse> findProducts(ProductCategory category) {
        List<Product> products = category == null
                ? productRepository.findByActiveTrueOrderByNameAsc()
                : productRepository.findByCategoryAndActiveTrueOrderByNameAsc(category);

        return products.stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return toResponse(product);
    }

    public List<ProductResponse> findLowStockProducts() {
        return productRepository
                .findByAvailableQuantityLessThanEqualAndActiveTrueOrderByAvailableQuantityAsc(
                        retailProperties.lowStockThreshold())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        // TODO: review validation
        Product product = new Product(
                request.name(),
                request.category(),
                request.price(),
                request.availableQuantity(),
                request.active() == null || request.active());

        return toResponse(productRepository.save(product));
    }

    public List<InventoryReportRow> generateInventoryReport() {
        List<Product> products =
                productRepository.findByActiveTrueOrderByNameAsc();

        List<InventoryReportRow> reportRows = new ArrayList<>();

        for (Product product : products) {
            BigDecimal inventoryValue = product.getPrice()
                    .multiply(BigDecimal.valueOf(
                            product.getAvailableQuantity()));

            boolean lowStock = product.getAvailableQuantity()
                    <= retailProperties.lowStockThreshold();

            reportRows.add(new InventoryReportRow(
                    product.getId(),
                    product.getName(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getAvailableQuantity(),
                    inventoryValue,
                    lowStock));
        }

        return reportRows;
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                retailProperties.defaultCurrency(),
                product.getAvailableQuantity(),
                product.isActive());
    }
}

