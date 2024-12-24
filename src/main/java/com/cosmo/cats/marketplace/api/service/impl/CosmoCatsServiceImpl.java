package com.cosmo.cats.marketplace.api.service.impl;

import com.cosmo.cats.marketplace.api.domain.CosmoCat;
import com.cosmo.cats.marketplace.api.service.CosmoCatsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CosmoCatsServiceImpl implements CosmoCatsService {

    List<CosmoCat> cosmoCats = new ArrayList<>(List.of(
            CosmoCat.builder().id(1L).name("CosmoCat 1").breed("Cosmic devon rex").price(11.99).build(),
            CosmoCat.builder().id(2L).name("CosmoCat 2").breed("Galactic bengal").price(22.99).build(),
            CosmoCat.builder().id(3L).name("CosmoCat 3").breed("Star Bombay").price(33.99).build()
    ));

    @Override
    public List<CosmoCat> getCosmoCats() {
        return cosmoCats;
    }
}