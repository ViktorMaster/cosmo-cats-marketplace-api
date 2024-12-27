package com.cosmo.cats.marketplace.api.data;

import com.cosmo.cats.marketplace.api.domain.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Optional<Product> getById(UUID id);

    List<Product> getAll();

    Product update(UUID id, Product updatedProduct);

    void delete(UUID id);

    Product addProduct(Product product);
}
