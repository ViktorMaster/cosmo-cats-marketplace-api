package com.cosmo.cats.marketplace.api.web.mapper;

import com.cosmo.cats.marketplace.api.domain.Product;
import com.cosmo.cats.marketplace.api.web.dto.product.ProductCreationDto;
import com.cosmo.cats.marketplace.api.web.dto.product.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    List<ProductDto> toProductDto(List<Product> products);

    ProductDto toProductDto(Product product);

    Product toProduct(ProductCreationDto productDto);

}