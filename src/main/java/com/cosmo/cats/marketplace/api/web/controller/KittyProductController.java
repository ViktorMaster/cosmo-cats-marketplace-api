package com.cosmo.cats.marketplace.api.web.controller;

import com.cosmo.cats.marketplace.api.featuretoggle.FeatureToggles;
import com.cosmo.cats.marketplace.api.featuretoggle.annotation.FeatureToggle;
import com.cosmo.cats.marketplace.api.service.KittyProductService;
import com.cosmo.cats.marketplace.api.web.dto.kittyproduct.KittyProductDto;
import com.cosmo.cats.marketplace.api.web.mapper.KittyProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kitty-products")
public class KittyProductController {
    private final KittyProductService kittyProductService;
    private final KittyProductDtoMapper kittyProductDtoMapper;

    @GetMapping
    @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    public ResponseEntity<List<KittyProductDto>> getAllKittyProducts() {
        return ResponseEntity.ok(kittyProductDtoMapper.toKittyProductDto(
                kittyProductService.getAllKittyProducts()
        ));
    }
}