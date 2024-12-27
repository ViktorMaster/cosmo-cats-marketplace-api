package com.cosmo.cats.marketplace.api.web.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class CosmicWordValidator implements ConstraintValidator<CosmicWordCheck, String> {
  private static final String COSMIC_TERMS_PATTERN = ".*\\b(star|galaxy|comet)\\b.*";
  private static final Pattern pattern = Pattern.compile(COSMIC_TERMS_PATTERN, Pattern.CASE_INSENSITIVE);
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return pattern.matcher(value).matches();
  }
}