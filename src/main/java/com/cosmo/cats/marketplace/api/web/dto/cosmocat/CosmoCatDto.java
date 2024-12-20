package com.cosmo.cats.marketplace.api.web.dto.cosmocat;

import lombok.Value;

@Value
public class CosmoCatDto {
    long id;
    String name;
    String breed;
    double price;
}
