package com.cosmo.cats.marketplace.api.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Product {
    UUID id;
    String name;
    String description;
    double price;
    UUID categoryId;
}