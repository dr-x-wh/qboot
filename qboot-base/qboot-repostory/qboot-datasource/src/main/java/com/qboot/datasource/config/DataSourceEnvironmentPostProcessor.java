package com.qboot.datasource.config;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public final class DataSourceEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final String CONFIG_LOCATION = "META-INF/qboot/datasource.yml";
    private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

    @Override
    public void postProcessEnvironment(@NonNull ConfigurableEnvironment environment, @NonNull SpringApplication application) {
        Resource resource = new ClassPathResource(CONFIG_LOCATION);
        if (!resource.exists()) {
            return;
        }

        try {


            List<PropertySource<?>> sources = loader.load("datasource-module-defaults", resource);

            for (int i = sources.size() - 1; i >= 0; i--) {
                environment.getPropertySources().addLast(sources.get(i));
            }

        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + CONFIG_LOCATION, e);
        }
    }

    @Override
    public int getOrder() {
        return ConfigDataEnvironmentPostProcessor.ORDER + 1;
    }
}
