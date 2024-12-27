package com.cosmo.cats.marketplace.api.service.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
  private static final String baseMessage = "Product with id %s not found";

  public ProductNotFoundException(UUID productId) {
    super(String.format(baseMessage, productId));
  }
}