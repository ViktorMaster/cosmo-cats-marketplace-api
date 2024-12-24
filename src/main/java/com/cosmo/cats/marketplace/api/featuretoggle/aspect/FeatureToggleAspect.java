package com.cosmo.cats.marketplace.api.featuretoggle.aspect;

import com.cosmo.cats.marketplace.api.featuretoggle.FeatureToggleService;
import com.cosmo.cats.marketplace.api.featuretoggle.FeatureToggles;
import com.cosmo.cats.marketplace.api.featuretoggle.annotation.FeatureToggle;
import com.cosmo.cats.marketplace.api.featuretoggle.exception.FeatureNotEnabledException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {
    private final FeatureToggleService featureToggleService;

    @Before(value = "@annotation(featureToggle)")
    public void checkFeatureToggle(FeatureToggle featureToggle){
        FeatureToggles toggle = featureToggle.value();

        if(!featureToggleService.checkFeatureToggle(toggle.getName())) {
            throw new FeatureNotEnabledException(toggle.getName());
        }
    }
}