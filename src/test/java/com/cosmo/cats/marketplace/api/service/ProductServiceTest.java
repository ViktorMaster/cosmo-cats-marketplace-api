package com.cosmo.cats.marketplace.api.service;

import com.cosmo.cats.marketplace.api.data.ProductRepository;
import com.cosmo.cats.marketplace.api.domain.Product;
import com.cosmo.cats.marketplace.api.service.exception.ProductAlreadyExistsException;
import com.cosmo.cats.marketplace.api.service.exception.ProductNotFoundException;
import com.cosmo.cats.marketplace.api.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductServiceTest {

    ProductRepository productRepository = new MockProductRepository();

    ProductService productService = new ProductServiceImpl(productRepository);

    private static Stream<Long> provideId() {
        return Stream.of(0L, 1L, 2L);
    }

    @Test
    void shouldReturnAllProducts() {
        var result = productService.getProducts();

        assertEquals(3, result.size());
    }

    @ParameterizedTest
    @MethodSource("provideId")
    void shouldReturnProductById(Long id) {
        var result = productService.getProduct(id);
        assertNotNull(result);
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenIdIsNonExistent() {
        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(4L));
    }

    @Test
    void shouldCreateProductSuccessfully() {
        var newProduct = Product.builder()
                .name("New name")
                .price(99.9)
                .description("Description")
                .categoryId(1L)
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
                        .categoryId(1L)
                        .build())
        );
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        var newProduct = Product.builder()
                .id(1L)
                .name("New name")
                .price(99.9)
                .description("Description")
                .categoryId(1L)
                .build();
        assertNotEquals(newProduct, productService.getProduct(1L));
        assertDoesNotThrow(() ->
                productService.updateProduct(1L, newProduct)
        );
        assertEquals(newProduct, productService.getProduct(1L));
    }

    @Test
    void shouldUpdateProductWithNewIdWhenProductIdIsNonExistent() {
        var newProduct = Product.builder()
                .id(4L)
                .name("New name")
                .price(99.9)
                .description("Description")
                .categoryId(1L)
                .build();
        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(4L));
        assertDoesNotThrow(() ->
                productService.updateProduct(4L, newProduct)
        );
        assertEquals(newProduct, productService.getProduct(4L));
    }

    @Test
    void shouldThrowDuplicateProductNameExceptionWhenUpdatingWithExistingName() {
        assertThrows(ProductAlreadyExistsException.class, () ->
                productService.updateProduct(2L, Product.builder()
                        .name("Star Helmet")
                        .price(99.9)
                        .description("Description")
                        .categoryId(1L)
                        .build())
        );
    }

}
