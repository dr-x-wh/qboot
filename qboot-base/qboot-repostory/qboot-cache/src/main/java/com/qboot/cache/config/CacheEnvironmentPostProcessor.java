package com.qboot.cache.config;

import com.qboot.common.tools.YamlLoader;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public final class CacheEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {
    private static final String CONFIG_LOCATION = "META-INF/qboot/cache.yml";

    @Override
    public void postProcessEnvironment(@NonNull ConfigurableEnvironment environment, @NonNull SpringApplication application) {
        YamlLoader.loadDefaults(environment, "cache-module-defaults", CONFIG_LOCATION);
    }

    @Override
    public int getOrder() {
        return ConfigDataEnvironmentPostProcessor.ORDER + 1;
    }
}
