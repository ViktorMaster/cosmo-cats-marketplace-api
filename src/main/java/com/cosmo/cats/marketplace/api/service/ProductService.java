package com.cosmo.cats.marketplace.api.service;

import com.cosmo.cats.marketplace.api.domain.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getProducts();
    Product getProduct(UUID id);
    Product createProduct(Product product);
    Product updateProduct(UUID id, Product product);
    void deleteProduct(UUID id);
}