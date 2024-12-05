package com.cosmo.cats.marketplace.api.data;

import com.cosmo.cats.marketplace.api.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> getById(Long id);

    List<Product> getAll();

    Product update(Long id, Product updatedProduct);

    void delete(Long id);

    Product addProduct(Product product);
}
