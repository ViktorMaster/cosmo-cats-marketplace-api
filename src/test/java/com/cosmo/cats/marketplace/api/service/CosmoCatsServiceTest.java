package com.cosmo.cats.marketplace.api.service;

import com.cosmo.cats.marketplace.api.service.impl.CosmoCatsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {CosmoCatsServiceImpl.class})
public class CosmoCatsServiceTest {
    @Autowired
    CosmoCatsService cosmoCatsService;

    @Test
    void shouldReturnAllCosmoCats() {
        var result = cosmoCatsService.getCosmoCats();

        assertEquals(3, result.size());
    }
}
