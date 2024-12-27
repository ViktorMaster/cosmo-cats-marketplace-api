package com.cosmo.cats.marketplace.api.web.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CosmicWordValidator.class)
@Documented
public @interface CosmicWordCheck {
  String NAME_SHOULD_CONTAIN_COSMIC_WORD = "Invalid Product Name: The field must contain a cosmic term (e.g., 'star', 'galaxy', 'comet')";

  String message() default NAME_SHOULD_CONTAIN_COSMIC_WORD;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}