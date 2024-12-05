package com.cosmo.cats.marketplace.api.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Product {
    Long id;
    String name;
    String description;
    double price;
    Long categoryId;
}