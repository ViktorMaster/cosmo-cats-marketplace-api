package com.cosmo.cats.marketplace.api.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CosmoCat {
    private long id;
    private String name;
    private String breed;
    private double price;
}