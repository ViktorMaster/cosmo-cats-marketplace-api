package com.cosmo.cats.marketplace.api.data;

import com.cosmo.cats.marketplace.api.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final List<Product> products = new ArrayList<>(buildAllProductsMock());

    @Override
    public Optional<Product> getById(UUID id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public Product update(UUID id, Product updatedProduct) {
        delete(id);
        Product newProduct = updatedProduct.toBuilder().id(id).build();
        products.add(newProduct);
        return newProduct;
    }

    @Override
    public void delete(UUID id) {
        var toBeDeleted = products.stream().filter(temp -> temp.getId().equals(id)).findFirst();
        if (toBeDeleted.isEmpty()) {
            return;
        }
        products.remove(toBeDeleted.get());
    }

    @Override
    public Product addProduct(Product product) {
        Product newProduct = product.toBuilder().id(UUID.randomUUID()).build();
        products.add(newProduct);
        return newProduct;
    }

    private List<Product> buildAllProductsMock() {
        return List.of(
                Product.builder()
                        .id(UUID.randomUUID())
                        .name("Star Helmet")
                        .description("A durable helmet for intergalactic travel.")
                        .price(17)
                        .categoryId(UUID.randomUUID())
                        .build(),
                Product.builder()
                        .id(UUID.randomUUID())
                        .name("Anti-Gravity Boots")
                        .description("Experience weightlessness on any surface.")
                        .price(50.5)
                        .categoryId(UUID.randomUUID())
                        .build(),
                Product.builder()
                        .id(UUID.randomUUID())
                        .name("Star Map")
                        .description("A holographic map of the known universe.")
                        .price(99.9)
                        .categoryId(UUID.randomUUID())
                        .build()
        );
    }
}