package com.yecraft.configuration;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ConfigurationTest {

    private Configuration config;
    private String version;
    @TempDir
    private Path path;

    @BeforeEach
    void setUp() {
        this.version = "1.0";
        Path path1 = path.resolve("config.yml");
        this.config = new Config(version, path1);
    }

    @Test
    @DisplayName("Config initialize")
    void canInitialize() {
        config.initialize();
        assertThat(config.getConfiguration()).isNotNull();
    }

    @Test
    void testLoadWithArgument() {
        config.load(path.toFile());
        assertThat(config.getConfiguration()).isNotNull();
    }

    @Test
    void testSaveWithArgument() {
        config.initialize();
        config.save(path.toFile());
        assertThat(path.toFile()).exists();
    }

    @Test
    void testLoadWithoutArgument() {
        config.load();
        assertThat(config.getConfiguration()).isNotNull();
    }

    @Test
    void testDefaults() {
        config.initialize();
        assertThat(config.getString("string")).hasValue("string");
        assertThat(config.getComments("string")).contains(List.of("comment"));
        assertThat(config.getString("map.string")).hasValue("string");
    }

    @Test
    void testSaveWithoutArguments() {
        config.initialize();
        config.save();
        assertThat(config.getConfiguration()).isNotNull();
        assertThat(this.path.toFile()).exists();
    }

    @Test
    void checkVersion() {
        config.initialize();
        Path path1 = path.resolve("config.yml");
        ConfigCopy copy = new ConfigCopy("1.1", path1);
        copy.initialize();
        assertThat(copy.getString("string")).hasValue("string-copy");
    }

    @Test
    //TODO solve the overwriting issue
    void checkIfDefaultsChangesAfterReload(){
        config.initialize();
        config.set("string", "string-change");
        config.save();
        config.initialize();
        assertThat(config.getString("string")).hasValue("string-change");
    }

    static class Config extends Configuration {

        public Config(String version, File file) {
            super(version, file);
        }

        public Config(String version, Path path) {
            super(version, path);
        }

        public Config(String version, @NotNull Logger logger, @NotNull File file) {
            super(version, logger, file);
        }

        public Config(String version, @NotNull Logger logger, @NotNull Path path) {
            super(version, logger, path);
        }

        @Override
        public Map<String, Object> defaults() {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("string", "string");
            map.put("map", Map.of("string", "string"));
            return map;
        }

        @Override
        public Map<String, List<String>> defaultComments() {
            Map<String, List<String>> map = new LinkedHashMap<>();
            map.put("string", List.of("comment"));
            return map;
        }
    }

    static class ConfigCopy extends Configuration {

        public ConfigCopy(String version, File file) {
            super(version, file);
        }

        public ConfigCopy(String version, Path path) {
            super(version, path);
        }

        public ConfigCopy(String version, @NotNull Logger logger, @NotNull File file) {
            super(version, logger, file);
        }

        public ConfigCopy(String version, @NotNull Logger logger, @NotNull Path path) {
            super(version, logger, path);
        }

        @Override
        public Map<String, Object> defaults() {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("string", "string-copy");
            return map;
        }

        @Override
        public Map<String, List<String>> defaultComments() {
            return Map.of();
        }
    }
}