package com.cosmo.cats.marketplace.api.web.controller;

import com.cosmo.cats.marketplace.api.service.ProductService;
import com.cosmo.cats.marketplace.api.web.dto.product.ProductCreationDto;
import com.cosmo.cats.marketplace.api.web.dto.product.ProductDto;
import com.cosmo.cats.marketplace.api.web.mapper.ProductDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductService productService;
  private final ProductDtoMapper productDtoMapper;

  @GetMapping
  public ResponseEntity<List<ProductDto>> getProducts() {
    return ResponseEntity.ok(productDtoMapper.toProductDto(productService.getProducts()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
    return ResponseEntity.ok(productDtoMapper.toProductDto(productService.getProduct(id)));
  }

  @PostMapping("/category/{categoryId}")
  public ResponseEntity<ProductDto> createProduct(
      @RequestBody @Valid ProductCreationDto productDto,
      @PathVariable Long categoryId) {
    return new ResponseEntity<>(productDtoMapper.toProductDto(
        productService.createProduct(
                productDtoMapper.toProduct(productDto)
                        .toBuilder()
                        .categoryId(categoryId)
                        .build())), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/category/{categoryId}")
  public ResponseEntity<ProductDto> updateProduct(
      @PathVariable Long id, @PathVariable Long categoryId,
      @RequestBody @Valid ProductCreationDto productDto) {
    return ResponseEntity.ok(productDtoMapper.toProductDto(
        productService.updateProduct(id, productDtoMapper.toProduct(productDto)
                .toBuilder()
                .categoryId(categoryId)
                .build())));
  }
}