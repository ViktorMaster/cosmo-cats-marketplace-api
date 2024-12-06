package com.cosmo.cats.marketplace.api.web.dto.product;

import com.cosmo.cats.marketplace.api.web.dto.validation.CosmicWordCheck;
import com.cosmo.cats.marketplace.api.web.dto.validation.ExtendedValidation;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@GroupSequence({ProductCreationDto.class, ExtendedValidation.class})
public class ProductCreationDto {
    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 100 characters")
    @CosmicWordCheck(groups = ExtendedValidation.class)
    String name;
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @NotNull(message = "description cannot be null")
    @NotBlank(message = "description cannot be blank")
    String description;
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false)
    double price;
}