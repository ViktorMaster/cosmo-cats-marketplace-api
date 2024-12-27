package com.cosmo.cats.marketplace.api.service;

import com.cosmo.cats.marketplace.api.data.ProductRepository;
import com.cosmo.cats.marketplace.api.domain.Product;
import com.cosmo.cats.marketplace.api.service.exception.ProductAlreadyExistsException;
import com.cosmo.cats.marketplace.api.service.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.getAll();
    }

    @Override
    public Product getProduct(UUID id) {
        return productRepository.getById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product createProduct(Product product) {
        if (existByName(product.getName())) {
            throw new ProductAlreadyExistsException(product.getName());
        }
        return productRepository.addProduct(product);
    }

    @Override
    public Product updateProduct(UUID id, Product updatedProduct) {
        if (!existById(id)) {
            return productRepository.addProduct(updatedProduct);
        }
        Product existingProduct = getProduct(id);
        if (existByName(updatedProduct.getName())
                && !updatedProduct.getName().equals(existingProduct.getName())) {
            throw new ProductAlreadyExistsException(updatedProduct.getName());
        }
        return productRepository.update(id, updatedProduct);
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.delete(id);
    }

    private boolean existByName(String productName) {
        return productRepository.getAll().stream()
                .anyMatch(product -> product.getName().equals(productName));
    }

    private boolean existById(UUID productId) {
        return productRepository.getAll().stream()
                .anyMatch(product -> product.getId().equals(productId));
    }

}
