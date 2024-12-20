package com.cosmo.cats.marketplace.api.service.impl;

import com.cosmo.cats.marketplace.api.domain.KittyProduct;
import com.cosmo.cats.marketplace.api.service.KittyProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KittyProductServiceImpl implements KittyProductService {
    List<KittyProduct> kittyProducts = new ArrayList<>(List.of(
            KittyProduct.builder().id(1L).name("KittyProduct 1").description("Description").price(1.99).build(),
            KittyProduct.builder().id(2L).name("KittyProduct 2").description("Description").price(2.99).build(),
            KittyProduct.builder().id(3L).name("KittyProduct 3").description("Description").price(3.99).build()
    ));

    @Override
    public List<KittyProduct> getAllKittyProducts() {
        return kittyProducts;
    }
}