package com.cosmo.cats.marketplace.api.featuretoggle.annotation;

import com.cosmo.cats.marketplace.api.featuretoggle.FeatureToggles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface EnabledFeatureToggle {
    FeatureToggles value();
}