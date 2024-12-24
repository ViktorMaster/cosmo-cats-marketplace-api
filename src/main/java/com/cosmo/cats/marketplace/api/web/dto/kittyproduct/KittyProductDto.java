package com.cosmo.cats.marketplace.api.web.dto.kittyproduct;

import lombok.Value;

@Value
public class KittyProductDto {
    long id;
    String name;
    String description;
    Double price;
}
