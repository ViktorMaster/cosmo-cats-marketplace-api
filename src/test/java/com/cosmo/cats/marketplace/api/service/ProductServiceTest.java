package com.cosmo.cats.marketplace.api.service;

import com.cosmo.cats.marketplace.api.data.ProductRepository;
import com.cosmo.cats.marketplace.api.domain.Product;
import com.cosmo.cats.marketplace.api.service.exception.ProductAlreadyExistsException;
import com.cosmo.cats.marketplace.api.service.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductServiceTest {

    ProductRepository productRepository = new MockProductRepository();

    ProductService productService = new ProductServiceImpl(productRepository);

    private final UUID EXISTING_ID = productService.getProducts().get(1).getId();;

    @Test
    void shouldReturnAllProducts() {
        var result = productService.getProducts();

        assertEquals(3, result.size());
    }

    @Test
    void shouldReturnProductById() {
        var result = productService.getProduct(EXISTING_ID);
        assertNotNull(result);
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenIdIsNonExistent() {
        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(UUID.randomUUID()));
    }

    @Test
    void shouldCreateProductSuccessfully() {
        var newProduct = Product.builder()
                .name("New name")
                .price(99.9)
                .description("Description")
                .categoryId(UUID.randomUUID())
                .build();

        assertDoesNotThrow(() -> productService.createProduct(newProduct));
    }

    @Test
    void shouldThrowDuplicateProductNameExceptionWhenCreatingWithExistingName() {
        assertThrows(ProductAlreadyExistsException.class, () ->
                productService.createProduct(Product.builder()
                        .name("Star Helmet")
                        .price(99.9)
                        .description("Description")
                        .categoryId(UUID.randomUUID())
                        .build())
        );
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        var newProduct = Product.builder()
                .id(EXISTING_ID)
                .name("New name")
                .price(99.9)
                .description("Description")
                .categoryId(UUID.randomUUID())
                .build();
        assertNotEquals(newProduct, productService.getProduct(EXISTING_ID));
        assertDoesNotThrow(() ->
                productService.updateProduct(EXISTING_ID, newProduct)
        );
        assertEquals(newProduct, productService.getProduct(EXISTING_ID));
    }

    @Test
    void shouldUpdateProductWithNewIdWhenProductIdIsNonExistent() {
        var id = UUID.randomUUID();
        var newProduct = Product.builder()
                .name("New name")
                .price(99.9)
                .description("Description")
                .categoryId(UUID.randomUUID())
                .build();
        var result = productService.getProducts();
        assertEquals(3, result.size());
        assertDoesNotThrow(() ->
                productService.updateProduct(id, newProduct)
        );
        result = productService.getProducts();
        assertEquals(4, result.size());
    }

    @Test
    void shouldThrowDuplicateProductNameExceptionWhenUpdatingWithExistingName() {
        assertThrows(ProductAlreadyExistsException.class, () ->
                productService.updateProduct(EXISTING_ID, Product.builder()
                        .name("Star Helmet")
                        .price(99.9)
                        .description("Description")
                        .categoryId(UUID.randomUUID())
                        .build())
        );
    }

}
