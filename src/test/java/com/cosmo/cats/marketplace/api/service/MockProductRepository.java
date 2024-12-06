package com.cosmo.cats.marketplace.api.service;

import com.cosmo.cats.marketplace.api.data.ProductRepository;
import com.cosmo.cats.marketplace.api.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockProductRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>(buildAllProductsMock());

    private Long nextId = 4L;

    private Long getNextId() {
        return nextId++;
    }

    @Override
    public Optional<Product> getById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public Product update(Long id, Product updatedProduct) {
        delete(id);
        Product newProduct = updatedProduct.toBuilder().id(id).build();
        products.add(newProduct);
        return newProduct;
    }

    @Override
    public void delete(Long id) {
        var toBeDeleted = products.stream().filter(temp -> temp.getId().equals(id)).findFirst();
        if (toBeDeleted.isEmpty()) {
            return;
        }
        products.remove(toBeDeleted.get());
    }

    @Override
    public Product addProduct(Product product) {
        Product newProduct = product.toBuilder().id(getNextId()).build();
        products.add(newProduct);
        return newProduct;
    }

    private List<Product> buildAllProductsMock() {
        return List.of(
                Product.builder()
                        .id(0L)
                        .name("Star Helmet")
                        .description("A durable helmet for intergalactic travel.")
                        .price(17)
                        .categoryId(1L)
                        .build(),
                Product.builder()
                        .id(1L)
                        .name("Anti-Gravity Boots")
                        .description("Experience weightlessness on any surface.")
                        .price(50.5)
                        .categoryId(2L)
                        .build(),
                Product.builder()
                        .id(2L)
                        .name("Star Map")
                        .description("A holographic map of the known universe.")
                        .price(99.9)
                        .categoryId(3L)
                        .build()
        );
    }
}