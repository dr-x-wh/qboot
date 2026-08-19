package com.qboot.common.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

@Slf4j
public final class YamlLoader {
    private static final YamlPropertySourceLoader YAML_PROPERTY_SOURCE_LOADER = new YamlPropertySourceLoader();

    private YamlLoader() {
    }

    public static void loadDefaults(ConfigurableEnvironment environment, String name, String location) {
        Resource resource = new ClassPathResource(location);
        if (!resource.exists()) {
            log.error("配置文件不存在：{}", location);
            return;
        }
        try {
            List<PropertySource<?>> sources = YAML_PROPERTY_SOURCE_LOADER.load(name, resource);
            sources.forEach(environment.getPropertySources()::addLast);
        } catch (IOException e) {
            throw new IllegalStateException("无法加载配置文件: " + location, e);
        }
    }
}
