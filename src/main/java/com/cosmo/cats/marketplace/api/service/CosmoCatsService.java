package com.cosmo.cats.marketplace.api.service;

import com.cosmo.cats.marketplace.api.domain.CosmoCat;

import java.util.List;

public interface CosmoCatsService {
    List<CosmoCat> getCosmoCats();
}