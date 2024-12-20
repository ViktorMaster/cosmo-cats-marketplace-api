package com.cosmo.cats.marketplace.api.web.mapper;

import com.cosmo.cats.marketplace.api.domain.CosmoCat;
import com.cosmo.cats.marketplace.api.web.dto.cosmocat.CosmoCatDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CosmoCatDtoMapper {
    List<CosmoCatDto> toCosmoCatDto(List<CosmoCat> cosmoCats);
}
