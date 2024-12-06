package com.cosmo.cats.marketplace.api.web.dto.product;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductDto {
    Long id;
    String name;
    String description;
    double price;
    Long categoryId;
}
