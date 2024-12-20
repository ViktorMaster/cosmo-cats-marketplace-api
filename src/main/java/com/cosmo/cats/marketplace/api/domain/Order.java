package com.cosmo.cats.marketplace.api.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Order {
    Long id;
    String status;
    List<Product> products;
}
