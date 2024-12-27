package com.cosmo.cats.marketplace.api.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Order {
    UUID id;
    String status;
    List<Product> products;
}
