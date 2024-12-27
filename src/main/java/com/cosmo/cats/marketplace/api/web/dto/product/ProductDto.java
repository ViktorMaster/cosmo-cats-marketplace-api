package com.cosmo.cats.marketplace.api.web.dto.product;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ProductDto {
    UUID id;
    String name;
    String description;
    double price;
    UUID categoryId;
}
