package com.cosmo.cats.marketplace.api.web;

import com.cosmo.cats.marketplace.api.domain.Product;
import com.cosmo.cats.marketplace.api.service.ProductService;
import com.cosmo.cats.marketplace.api.service.exception.ProductNotFoundException;
import com.cosmo.cats.marketplace.api.web.dto.product.ProductCreationDto;
import com.cosmo.cats.marketplace.api.web.dto.product.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Product Controller Tests")
public class ProductControllerIT {
    private static final Long PRODUCT_ID = 0L;
    private static final Long CATEGORY_ID = 1L;
    private final List<ProductDto> productDtoList = buildProductListDto();
    private ProductDto productDto;
    private Product mockProduct;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productDto = ProductDto.builder()
                .id(PRODUCT_ID)
                .name("Star Helmet")
                .description("A durable helmet for intergalactic travel.")
                .price(17)
                .categoryId(CATEGORY_ID)
                .build();
        mockProduct = Product.builder()
                .id(PRODUCT_ID)
                .name("Star Helmet")
                .description("A durable helmet for intergalactic travel.")
                .price(17)
                .categoryId(CATEGORY_ID)
                .build();
    }

    @Test
    void shouldCreateProduct() throws Exception {
        when(productService.createProduct(any())).thenReturn(mockProduct);
        mockMvc.perform(post("/api/v1/products/category/{categoryId}", CATEGORY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.description").value(productDto.getDescription()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()))
                .andExpect(jsonPath("$.categoryId").value(productDto.getCategoryId()));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidProductDto")
    void createProductShouldFailValidation(ProductCreationDto productCreationDto) throws Exception {
        mockMvc.perform(post("/api/v1/products/category/{categoryId}", CATEGORY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreationDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams").isNotEmpty());
    }

    @Test
    void shouldGetProductById() throws Exception {
        when(productService.getProduct(PRODUCT_ID)).thenReturn(mockProduct);

        mockMvc.perform(get("/api/v1/products/{id}", PRODUCT_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.description").value(productDto.getDescription()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()))
                .andExpect(jsonPath("$.categoryId").value(productDto.getCategoryId()));
    }

    @Test
    void getProductByIdShouldBeNotFound() throws Exception {
        when(productService.getProduct(any())).thenThrow(ProductNotFoundException.class);
        mockMvc.perform(get("/api/v1/product/{id}", 99L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Not Found"));
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        when(productService.getProducts()).thenReturn(buildProductList());

        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(buildProductList())));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductCreationDto updatedProductDto = ProductCreationDto.builder()
                .name("Star Helmet")
                .description("A durable helmet for intergalactic travel.")
                .price(17)
                .build();
        Product updatedProduct = Product.builder()
                .id(PRODUCT_ID)
                .name(updatedProductDto.getName())
                .description(updatedProductDto.getDescription())
                .categoryId(CATEGORY_ID)
                .price(updatedProductDto.getPrice())
                .build();

        when(productService.updateProduct(any(), any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/v1/products/{id}/category/{categoryId}", PRODUCT_ID, CATEGORY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedProductDto.getName()))
                .andExpect(jsonPath("$.description").value(updatedProductDto.getDescription()))
                .andExpect(jsonPath("$.price").value(updatedProductDto.getPrice()))
                .andExpect(jsonPath("$.categoryId").value(CATEGORY_ID));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidProductDto")
    void updateProductShouldFailValidation(ProductCreationDto productCreationDto) throws Exception {
        mockMvc.perform(put("/api/v1/products/{id}/category/{categoryId}", PRODUCT_ID, CATEGORY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreationDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams").isNotEmpty());
    }

    @Test
    void shouldUpdateProductWithNewIdWhenProductIdIsNonExistent() throws Exception {
        when(productService.updateProduct(any(), any(Product.class))).thenReturn(mockProduct);
        mockMvc.perform(put("/api/v1/products/{id}/category/{categoryId}", PRODUCT_ID, CATEGORY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.description").value(productDto.getDescription()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()))
                .andExpect(jsonPath("$.categoryId").value(productDto.getCategoryId()));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(PRODUCT_ID);

        mockMvc.perform(delete("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private static Stream<ProductCreationDto> provideInvalidProductDto() {
        return Stream.of(
                buildProductCreationDto(""),
                buildProductCreationDto("Name without required words"),
                buildProductCreationDto(null),
                buildProductCreationDto("galaxy mock").toBuilder().price(-5.5).build()
        );
    }

    private static ProductCreationDto buildProductCreationDto(String name) {
        return ProductCreationDto.builder().name(name).description("Mock description").price(
                99.9).build();
    }


    private static List<ProductDto> buildProductListDto() {
        return List.of(
                ProductDto.builder()
                        .id(0L)
                        .name("Star Helmet")
                        .description("A durable helmet for intergalactic travel.")
                        .price(17)
                        .categoryId(1L)
                        .build(),
                ProductDto.builder()
                        .id(1L)
                        .name("Anti-Gravity Boots")
                        .description("Experience weightlessness on any surface.")
                        .price(50.5)
                        .categoryId(2L)
                        .build(),
                ProductDto.builder()
                        .id(2L)
                        .name("Star Map")
                        .description("A holographic map of the known universe.")
                        .price(99.9)
                        .categoryId(3L)
                        .build()
        );
    }

    private static List<Product> buildProductList() {
        return buildProductListDto().stream()
                .map(dto -> Product.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .description(dto.getDescription())
                        .categoryId(dto.getCategoryId())
                        .price(dto.getPrice())
                        .build())
                .toList();
    }
}
