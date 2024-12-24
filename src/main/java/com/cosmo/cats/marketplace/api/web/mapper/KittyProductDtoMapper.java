package com.cosmo.cats.marketplace.api.web.mapper;

import com.cosmo.cats.marketplace.api.domain.KittyProduct;
import com.cosmo.cats.marketplace.api.web.dto.kittyproduct.KittyProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KittyProductDtoMapper {
    List<KittyProductDto> toKittyProductDto(List<KittyProduct> kittyProducts);
}
