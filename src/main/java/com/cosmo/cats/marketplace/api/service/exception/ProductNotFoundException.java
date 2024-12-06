package com.cosmo.cats.marketplace.api.service.exception;

public class ProductNotFoundException extends RuntimeException {
  private static final String baseMessage = "Product with id %s not found";

  public ProductNotFoundException(Long productId) {
    super(String.format(baseMessage, productId));
  }
}