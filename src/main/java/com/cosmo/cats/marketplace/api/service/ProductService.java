package com.cosmo.cats.marketplace.api.service;

import com.cosmo.cats.marketplace.api.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    Product getProduct(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}