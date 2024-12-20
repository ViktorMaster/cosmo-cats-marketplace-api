package com.cosmo.cats.marketplace.api.service;

import com.cosmo.cats.marketplace.api.service.impl.KittyProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {KittyProductServiceImpl.class})
public class KittyProductServiceTest {
    @Autowired
    KittyProductService kittyProductService;

    @Test
    void shouldReturnAllKittyProducts() {
        var result = kittyProductService.getAllKittyProducts();

        assertEquals(3, result.size());
    }
}
