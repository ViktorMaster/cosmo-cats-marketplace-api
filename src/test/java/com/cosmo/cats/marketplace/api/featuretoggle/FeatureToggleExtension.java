package com.cosmo.cats.marketplace.api.featuretoggle;

import com.cosmo.cats.marketplace.api.featuretoggle.annotation.DisabledFeatureToggle;
import com.cosmo.cats.marketplace.api.featuretoggle.annotation.EnabledFeatureToggle;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class FeatureToggleExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {

        context.getTestMethod().ifPresent(method -> {

            FeatureToggleService featureToggleService = getFeatureToggleService(context);

            if (method.isAnnotationPresent(EnabledFeatureToggle.class)) {
                EnabledFeatureToggle enabledFeatureToggleAnnotation = method.getAnnotation(EnabledFeatureToggle.class);
                featureToggleService.enableFeature(enabledFeatureToggleAnnotation.value().getName());
            } else if (method.isAnnotationPresent(DisabledFeatureToggle.class)) {
                DisabledFeatureToggle disabledFeatureToggleAnnotation = method.getAnnotation(DisabledFeatureToggle.class);
                featureToggleService.disableFeature(disabledFeatureToggleAnnotation.value().getName());
            }
        });
    }

    @Override
    public void afterEach(ExtensionContext context) {
        context.getTestMethod().ifPresent(method -> {
            String featureName = null;

            if (method.isAnnotationPresent(EnabledFeatureToggle.class)) {
                EnabledFeatureToggle enabledFeatureToggleAnnotation = method.getAnnotation(EnabledFeatureToggle.class);
                featureName = enabledFeatureToggleAnnotation.value().getName();
            } else if (method.isAnnotationPresent(DisabledFeatureToggle.class)) {
                DisabledFeatureToggle disabledFeatureToggleAnnotation = method.getAnnotation(DisabledFeatureToggle.class);
                featureName = disabledFeatureToggleAnnotation.value().getName();
            }
            if (featureName != null) {
                FeatureToggleService featureToggleService = getFeatureToggleService(context);
                if (getFeatureNamePropertyAsBoolean(context, featureName)) {
                    featureToggleService.enableFeature(featureName);
                } else {
                    featureToggleService.disableFeature(featureName);
                }
            }
        });
    }

    private boolean getFeatureNamePropertyAsBoolean(ExtensionContext context, String featureName) {
        Environment environment = SpringExtension.getApplicationContext(context).getEnvironment();
        return environment.getProperty("application.feature.toggles." + featureName, Boolean.class, Boolean.FALSE);
    }

    private FeatureToggleService getFeatureToggleService(ExtensionContext context) {
        return SpringExtension.getApplicationContext(context).getBean(FeatureToggleService.class);
    }

}