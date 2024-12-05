package com.cosmo.cats.marketplace.api.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Category {
    Long id;
    String name;
}
