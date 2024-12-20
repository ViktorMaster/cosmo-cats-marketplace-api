package com.cosmo.cats.marketplace.api.web.controller;

import com.cosmo.cats.marketplace.api.featuretoggle.FeatureToggles;
import com.cosmo.cats.marketplace.api.featuretoggle.annotation.FeatureToggle;
import com.cosmo.cats.marketplace.api.service.CosmoCatsService;
import com.cosmo.cats.marketplace.api.web.dto.cosmocat.CosmoCatDto;
import com.cosmo.cats.marketplace.api.web.mapper.CosmoCatDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cosmo-cats")
public class CosmoCatsController {
    private final CosmoCatsService cosmoCatsService;
    private final CosmoCatDtoMapper cosmoCatDtoMapper;

    @GetMapping
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public ResponseEntity<List<CosmoCatDto>> getCosmoCats() {
        return ResponseEntity.ok(cosmoCatDtoMapper.toCosmoCatDto(
                cosmoCatsService.getCosmoCats()
        ));
    }
}