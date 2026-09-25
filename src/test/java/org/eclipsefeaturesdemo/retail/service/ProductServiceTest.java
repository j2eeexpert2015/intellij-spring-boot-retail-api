package org.eclipsefeaturesdemo.retail.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.eclipsefeaturesdemo.retail.config.RetailProperties;
import org.eclipsefeaturesdemo.retail.dto.ProductRequest;
import org.eclipsefeaturesdemo.retail.dto.ProductResponse;
import org.eclipsefeaturesdemo.retail.exception.ProductNotFoundException;
import org.eclipsefeaturesdemo.retail.model.Product;
import org.eclipsefeaturesdemo.retail.model.ProductCategory;
import org.eclipsefeaturesdemo.retail.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        RetailProperties retailProperties = new RetailProperties(5, "USD", "support@retail-demo.com");
        productService = new ProductService(productRepository, retailProperties);
    }

    @Test
    void shouldFindActiveProducts() {
        Product product = product("Wireless Mouse", ProductCategory.ELECTRONICS, "25.99", 12);
        ReflectionTestUtils.setField(product, "id", 1L);

        when(productRepository.findByActiveTrueOrderByNameAsc()).thenReturn(List.of(product));

        List<ProductResponse> products = productService.findProducts(null);

        assertThat(products).hasSize(1);
        assertThat(products.getFirst().name()).isEqualTo("Wireless Mouse");
        assertThat(products.getFirst().currency()).isEqualTo("USD");
    }

    @Test
    void shouldThrowWhenProductIsMissing() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.findById(99L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void shouldCreateProduct() {
        ProductRequest request = new ProductRequest(
                "Desk Lamp",
                ProductCategory.HOME,
                new BigDecimal("34.99"),
                3,
                true);

        Product savedProduct = product("Desk Lamp", ProductCategory.HOME, "34.99", 3);
        ReflectionTestUtils.setField(savedProduct, "id", 10L);

        when(productRepository.save(org.mockito.ArgumentMatchers.any(Product.class))).thenReturn(savedProduct);

        ProductResponse response = productService.create(request);

        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.name()).isEqualTo("Desk Lamp");
        verify(productRepository).save(org.mockito.ArgumentMatchers.any(Product.class));
    }

    private Product product(String name, ProductCategory category, String price, int availableQuantity) {
        return new Product(name, category, new BigDecimal(price), availableQuantity, true);
    }
}
