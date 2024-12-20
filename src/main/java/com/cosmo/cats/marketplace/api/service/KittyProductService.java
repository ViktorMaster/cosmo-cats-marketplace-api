package com.cosmo.cats.marketplace.api.service;

import com.cosmo.cats.marketplace.api.domain.KittyProduct;

import java.util.List;

public interface KittyProductService {
    List<KittyProduct> getAllKittyProducts();
}